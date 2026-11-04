package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;

public interface SearchProfessionalsUseCase {
    PageResult<Professional> searchProfessionals(Long categoryId, String text, PageRequest pageRequest);
}
