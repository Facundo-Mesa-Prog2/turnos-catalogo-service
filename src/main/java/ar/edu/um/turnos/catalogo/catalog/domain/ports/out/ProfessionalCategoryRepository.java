package ar.edu.um.turnos.catalogo.catalog.domain.ports.out;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import java.util.List;
import java.util.Optional;

public interface ProfessionalCategoryRepository {
    ProfessionalCategory save(ProfessionalCategory category);
    Optional<ProfessionalCategory> findById(Long id);
    List<ProfessionalCategory> findAllEnabled();
}
