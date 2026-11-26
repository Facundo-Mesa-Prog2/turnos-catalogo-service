package ar.edu.um.turnos.catalogo.catalog.infrastructure.messaging;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SyncCatalogUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogChangeKafkaListener {

    private final SyncCatalogUseCase syncCatalogUseCase;

    @KafkaListener(topics = "${catedra.kafka.topic}", containerFactory = "catedraKafkaListenerContainerFactory")
    public void onCatalogChange(String message) {
        log.info("Aviso de cambio de catalogo recibido desde Kafka: {}", message);

        try {
            CatalogSyncResult result = syncCatalogUseCase.syncCatalog();
            log.info(
                "Sincronizacion disparada por Kafka: modo={} versionesAplicadas={} versionLocal={}",
                result.getMode(),
                result.getAppliedVersions(),
                result.getVersion()
            );
        } catch (Exception e) {
            log.error("Fallo la sincronizacion disparada por Kafka: {}", e.getMessage(), e);
        }
    }
}
