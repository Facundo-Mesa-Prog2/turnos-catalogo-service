package ar.edu.um.turnos.catalogo.catalog.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSyncResult {

    private Long version;
    private Mode mode;
    private int appliedVersions;

    public enum Mode {
        UP_TO_DATE,
        INCREMENTAL,
        SNAPSHOT,
    }
}
