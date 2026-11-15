package ar.edu.um.turnos.catalogo.catalog.domain.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSyncState {

    private Long snapshotVersion;
    private Instant lastSyncedAt;
}
