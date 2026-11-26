package ar.edu.um.turnos.catalogo.catalog.infrastructure.startup;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SyncCatalogUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogSyncStartupListener {

    private final SyncCatalogUseCase syncCatalogUseCase;

    @Value("${catedra.sync.on-startup:true}")
    private boolean enabled;

    @EventListener(ApplicationReadyEvent.class)
    public void syncOnStartup() {
        if (!enabled) {
            log.info("Sincronizacion al arrancar desactivada (catedra.sync.on-startup=false)");
            return;
        }

        log.info("Iniciando sincronizacion del catalogo al arrancar");
        try {
            CatalogSyncResult result = syncCatalogUseCase.syncCatalog();
            log.info(
                "Sincronizacion al arrancar completada: modo={} versionesAplicadas={} versionLocal={}",
                result.getMode(),
                result.getAppliedVersions(),
                result.getVersion()
            );
        } catch (Exception e) {
            log.error("Fallo la sincronizacion al arrancar, la aplicacion sigue funcionando: {}", e.getMessage(), e);
        }
    }
}
