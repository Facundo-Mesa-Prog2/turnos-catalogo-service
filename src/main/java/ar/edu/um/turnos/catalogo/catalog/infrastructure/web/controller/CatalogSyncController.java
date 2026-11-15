package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.controller;

import ar.edu.um.turnos.catalogo.catalog.application.service.CatalogService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog-sync")
@RequiredArgsConstructor
public class CatalogSyncController {

    private final CatalogService catalogService;

    @PostMapping("/snapshot")
    public ResponseEntity<Map<String, Object>> loadSnapshot() {
        Long version = catalogService.loadSnapshot();
        return ResponseEntity.ok(Map.of("snapshotVersion", version));
    }
}
