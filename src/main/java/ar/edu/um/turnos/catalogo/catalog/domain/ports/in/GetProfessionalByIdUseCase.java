package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import java.util.Optional;

public interface GetProfessionalByIdUseCase {
    Optional<Professional> getProfessionalById(Long id);
}
