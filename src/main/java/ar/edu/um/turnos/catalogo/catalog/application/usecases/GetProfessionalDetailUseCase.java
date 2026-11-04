package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalWithCategory;
import java.util.Optional;

public interface GetProfessionalDetailUseCase {
    Optional<ProfessionalWithCategory> getProfessionalDetail(Long id);
}
