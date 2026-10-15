package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import java.util.List;

public interface SearchProfessionalsUseCase {
    List<Professional> searchProfessionals(Long categoryId);
}
