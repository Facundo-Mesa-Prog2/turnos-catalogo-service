package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.GetProfessionalByIdUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// @Component
@RequiredArgsConstructor
public class GetProfessionalByIdUseCaseImpl implements GetProfessionalByIdUseCase {

    private final ProfessionalRepository professionalRepository;

    @Override
    public Optional<Professional> getProfessionalById(Long id) {
        return professionalRepository.findById(id);
    }
}
