package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper;

import ar.edu.um.turnos.catalogo.catalog.application.service.CatalogService;
import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalWithCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.PageResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.ProfessionalResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper.ProfessionalDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final CatalogService catalogService;
    private final ProfessionalDtoMapper mapper;

    @GetMapping
    public ResponseEntity<PageResponse<ProfessionalResponse>> search(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String text,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = PageRequest.builder().page(page).size(size).build();
        PageResult<Professional> result = catalogService.searchProfessionals(categoryId, text, pageRequest);

        PageResponse<ProfessionalResponse> response = mapper.toPageResponse(result, professional -> mapper.toResponse(professional, null));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        ProfessionalWithCategory detail = catalogService
            .getProfessionalDetail(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profesional no encontrado"));

        String categoryName = detail.getCategory() != null ? detail.getCategory().getName() : null;

        return ResponseEntity.ok(mapper.toResponse(detail.getProfessional(), categoryName));
    }
}
