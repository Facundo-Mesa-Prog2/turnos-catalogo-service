package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.application.service.CatalogChangeApplier;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogChangeSet;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncMetadata;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncResult;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncState;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.LoadCatalogSnapshotUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SyncCatalogUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogChangesPort;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSyncStateRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SyncCatalogUseCaseImpl implements SyncCatalogUseCase {

    private final CatalogChangesPort catalogChangesPort;
    private final CatalogSyncStateRepository syncStateRepository;
    private final LoadCatalogSnapshotUseCase loadCatalogSnapshotUseCase;
    private final CatalogChangeApplier changeApplier;

    @Override
    public CatalogSyncResult syncCatalog() {
        Optional<CatalogSyncState> localState = syncStateRepository.find();

        if (localState.isEmpty()) {
            log.info("No hay version local guardada: se carga el snapshot completo");
            return rebuildFromSnapshot();
        }

        long localVersion = localState.orElseThrow().getSnapshotVersion();
        CatalogSyncMetadata metadata = catalogChangesPort.fetchMetadata();

        if (localVersion >= metadata.getCurrentVersion()) {
            log.info("El catalogo local ya esta al dia en la version {}", localVersion);
            return CatalogSyncResult.builder().version(localVersion).mode(CatalogSyncResult.Mode.UP_TO_DATE).appliedVersions(0).build();
        }

        if (localVersion < metadata.getOldestAvailableVersion()) {
            log.info(
                "La version local {} quedo fuera de la ventana de Redis (la mas antigua disponible es {}): se reconstruye con snapshot",
                localVersion,
                metadata.getOldestAvailableVersion()
            );
            return rebuildFromSnapshot();
        }

        int applied = 0;

        for (long version = localVersion + 1; version <= metadata.getCurrentVersion(); version++) {
            Optional<CatalogChangeSet> changeSet = catalogChangesPort.fetchChanges(version);

            if (changeSet.isEmpty()) {
                log.warn("No se encontro la version {} en Redis: se reconstruye con snapshot", version);
                return rebuildFromSnapshot();
            }

            changeApplier.apply(changeSet.orElseThrow());
            applied++;
        }

        log.info("Catalogo sincronizado hasta la version {} aplicando {} versiones", metadata.getCurrentVersion(), applied);

        return CatalogSyncResult.builder()
            .version(metadata.getCurrentVersion())
            .mode(CatalogSyncResult.Mode.INCREMENTAL)
            .appliedVersions(applied)
            .build();
    }

    private CatalogSyncResult rebuildFromSnapshot() {
        Long version = loadCatalogSnapshotUseCase.loadSnapshot();

        return CatalogSyncResult.builder().version(version).mode(CatalogSyncResult.Mode.SNAPSHOT).appliedVersions(0).build();
    }
}
