package ar.edu.um.turnos.catalogo.catalog.application.service;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogChangeSet;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncState;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogChangesPort;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSyncStateRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalCategoryRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.WeeklyScheduleRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CatalogChangeApplier {

    private final CatalogChangesPort catalogChangesPort;
    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository scheduleRepository;
    private final CatalogSyncStateRepository syncStateRepository;

    @Transactional
    public void apply(CatalogChangeSet changeSet) {
        catalogChangesPort.fetchCategoriesByIds(changeSet.getCategoryIds()).forEach(categoryRepository::save);

        catalogChangesPort.fetchProfessionalsByIds(changeSet.getProfessionalIds()).forEach(professionalRepository::save);

        catalogChangesPort.fetchSchedulesByIds(changeSet.getScheduleIds()).forEach(scheduleRepository::save);

        syncStateRepository.save(CatalogSyncState.builder().snapshotVersion(changeSet.getVersion()).lastSyncedAt(Instant.now()).build());
    }
}
