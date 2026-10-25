package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.controller;

import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.ListCategoriesUseCase;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.CategoryResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper.CategoryDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ListCategoriesUseCase listCategoriesUseCase;
    private final CategoryDtoMapper categoryDtoMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listCategories() {
        List<CategoryResponse> responses = listCategoriesUseCase
            .listCategories()
            .stream()
            .map(categoryDtoMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
