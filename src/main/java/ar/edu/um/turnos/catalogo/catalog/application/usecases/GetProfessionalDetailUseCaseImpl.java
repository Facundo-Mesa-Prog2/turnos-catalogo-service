package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalWithCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalCategoryRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProfessionalDetailUseCaseImpl implements GetProfessionalDetailUseCase {

    private final ProfessionalRepository professionalRepository;
    private final ProfessionalCategoryRepository categoryRepository;

    @Override
    public Optional<ProfessionalWithCategory> getProfessionalDetail(Long id) {
        return professionalRepository
            .findById(id)
            .filter(Professional::isEnabled)
            .map(professional ->
                ProfessionalWithCategory.builder()
                    .professional(professional)
                    .category(categoryRepository.findById(professional.getCategoryId()).orElse(null))
                    .build()
            );
    }
}
