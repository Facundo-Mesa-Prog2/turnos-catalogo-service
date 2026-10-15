package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.ListCategoriesUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCategoriesUseCaseImpl implements ListCategoriesUseCase {

    private final ProfessionalCategoryRepository categoryRepository;

    @Override
    public List<ProfessionalCategory> listCategories() {
        return categoryRepository.findAllEnabled();
    }
}
