package ar.edu.um.turnos.catalogo.catalog.domain.ports.out;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogChangeSet;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncMetadata;
import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CatalogChangesPort {
    CatalogSyncMetadata fetchMetadata();

    Optional<CatalogChangeSet> fetchChanges(long version);

    List<ProfessionalCategory> fetchCategoriesByIds(Collection<Long> ids);

    List<Professional> fetchProfessionalsByIds(Collection<Long> ids);

    List<WeeklySchedule> fetchSchedulesByIds(Collection<Long> ids);
}
