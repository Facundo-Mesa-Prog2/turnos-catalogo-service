package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.PageResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.ProfessionalResponse;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalDtoMapper {

    public ProfessionalResponse toResponse(Professional professional, String categoryName) {
        if (professional == null) {
            return null;
        }
        return ProfessionalResponse.builder()
            .id(professional.getId())
            .firstName(professional.getFirstName())
            .lastName(professional.getLastName())
            .categoryId(professional.getCategoryId())
            .categoryName(categoryName)
            .build();
    }

    public <T> PageResponse<T> toPageResponse(PageResult<Professional> result, Function<Professional, T> converter) {
        List<T> content = result.getContent().stream().map(converter).collect(Collectors.toList());

        int totalPages = result.getSize() == 0 ? 0 : (int) Math.ceil((double) result.getTotalElements() / result.getSize());

        return PageResponse.<T>builder()
            .content(content)
            .totalElements(result.getTotalElements())
            .page(result.getPage())
            .size(result.getSize())
            .totalPages(totalPages)
            .build();
    }
}
