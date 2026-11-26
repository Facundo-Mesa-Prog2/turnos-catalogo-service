package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSnapshot;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncState;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.LoadCatalogSnapshotUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSnapshotPort;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSyncStateRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalCategoryRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.WeeklyScheduleRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadCatalogSnapshotUseCaseImpl implements LoadCatalogSnapshotUseCase {

    private final CatalogSnapshotPort catalogSnapshotPort;
    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository scheduleRepository;
    private final CatalogSyncStateRepository syncStateRepository;

    @Override
    @Transactional
    public Long loadSnapshot() {
        CatalogSnapshot snapshot = catalogSnapshotPort.fetchSnapshot();

        snapshot.getCategories().forEach(categoryRepository::save);
        snapshot.getProfessionals().forEach(professionalRepository::save);
        snapshot.getSchedules().forEach(scheduleRepository::save);
        syncStateRepository.save(
            CatalogSyncState.builder().snapshotVersion(snapshot.getSnapshotVersion()).lastSyncedAt(Instant.now()).build()
        );

        log.info(
            "Snapshot version {} aplicado: {} categorias, {} profesionales, {} agendas",
            snapshot.getSnapshotVersion(),
            snapshot.getCategories().size(),
            snapshot.getProfessionals().size(),
            snapshot.getSchedules().size()
        );

        return snapshot.getSnapshotVersion();
    }
}
