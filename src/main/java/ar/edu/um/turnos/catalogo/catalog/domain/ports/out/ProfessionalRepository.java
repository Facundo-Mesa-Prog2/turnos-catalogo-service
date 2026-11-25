package ar.edu.um.turnos.catalogo.catalog.domain.ports.out;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import java.util.Optional;

public interface ProfessionalRepository {
    Professional save(Professional professional);
    Optional<Professional> findById(Long id);
    PageResult<Professional> search(Long categoryId, String text, PageRequest pageRequest);
}
