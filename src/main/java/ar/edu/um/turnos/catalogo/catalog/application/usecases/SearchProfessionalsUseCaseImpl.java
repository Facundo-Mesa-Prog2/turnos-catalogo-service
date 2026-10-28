package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SearchProfessionalsUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// @Component
@RequiredArgsConstructor
public class SearchProfessionalsUseCaseImpl implements SearchProfessionalsUseCase {

    private final ProfessionalRepository professionalRepository;

    @Override
    public List<Professional> searchProfessionals(Long categoryId) {
        if (categoryId == null) {
            return professionalRepository.findAllEnabled();
        }
        return professionalRepository.findEnabledByCategoryId(categoryId);
    }
}
