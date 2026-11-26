package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "catalog_sync_state")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSyncStateEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long snapshotVersion;

    private Instant lastSyncedAt;
}
