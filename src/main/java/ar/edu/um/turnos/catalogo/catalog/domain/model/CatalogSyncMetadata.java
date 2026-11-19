package ar.edu.um.turnos.catalogo.catalog.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSyncMetadata {

    private Long currentVersion;
    private Long oldestAvailableVersion;
}
