package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SearchProfessionalsUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProfessionalsUseCaseImpl implements SearchProfessionalsUseCase {

    private final ProfessionalRepository professionalRepository;

    @Override
    public PageResult<Professional> searchProfessionals(Long categoryId, String text, PageRequest pageRequest) {
        String normalizedText = (text == null || text.isBlank()) ? null : text.trim();
        return professionalRepository.search(categoryId, normalizedText, pageRequest);
    }
}
