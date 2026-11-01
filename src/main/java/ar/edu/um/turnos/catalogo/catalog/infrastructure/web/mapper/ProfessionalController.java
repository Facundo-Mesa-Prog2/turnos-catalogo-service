package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.GetProfessionalByIdUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SearchProfessionalsUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalCategoryRepository;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.PageResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.ProfessionalResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper.ProfessionalDtoMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final SearchProfessionalsUseCase searchProfessionalsUseCase;
    private final GetProfessionalByIdUseCase getProfessionalByIdUseCase;
    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalDtoMapper mapper;

    @GetMapping
    public ResponseEntity<PageResponse<ProfessionalResponse>> search(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String text,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = PageRequest.builder().page(page).size(size).build();
        PageResult<Professional> result = searchProfessionalsUseCase.searchProfessionals(categoryId, text, pageRequest);

        Map<Long, String> categoryNames = new HashMap<>();

        PageResponse<ProfessionalResponse> response = mapper.toPageResponse(result, professional ->
            mapper.toResponse(
                professional,
                categoryNames.computeIfAbsent(professional.getCategoryId(), id ->
                    categoryRepository.findById(id).map(ProfessionalCategory::getName).orElse(null)
                )
            )
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        Professional professional = getProfessionalByIdUseCase
            .getProfessionalById(id)
            .filter(Professional::isEnabled)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profesional no encontrado"));

        String categoryName = categoryRepository.findById(professional.getCategoryId()).map(ProfessionalCategory::getName).orElse(null);

        return ResponseEntity.ok(mapper.toResponse(professional, categoryName));
    }
}
