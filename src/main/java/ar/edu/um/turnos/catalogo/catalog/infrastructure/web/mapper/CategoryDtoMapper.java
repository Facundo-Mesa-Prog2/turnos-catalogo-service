package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapper {

    public CategoryResponse toResponse(ProfessionalCategory category) {
        if (category == null) {
            return null;
        }
        return CategoryResponse.builder().id(category.getId()).name(category.getName()).description(category.getDescription()).build();
    }
}
