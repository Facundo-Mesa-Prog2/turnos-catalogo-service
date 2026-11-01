package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalWithCategory;
import java.util.Optional;

public interface GetProfessionalDetailUseCase {
    Optional<ProfessionalWithCategory> getProfessionalDetail(Long id);
}
