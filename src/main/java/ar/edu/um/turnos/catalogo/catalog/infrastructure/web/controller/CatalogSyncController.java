package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.controller;

import ar.edu.um.turnos.catalogo.catalog.application.service.CatalogService;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncResult;
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

    @PostMapping
    public ResponseEntity<Map<String, Object>> sync() {
        CatalogSyncResult result = catalogService.syncCatalog();

        return ResponseEntity.ok(
            Map.of("version", result.getVersion(), "mode", result.getMode().name(), "appliedVersions", result.getAppliedVersions())
        );
    }

    @PostMapping("/snapshot")
    public ResponseEntity<Map<String, Object>> loadSnapshot() {
        Long version = catalogService.loadSnapshot();

        return ResponseEntity.ok(Map.of("snapshotVersion", version));
    }
}
