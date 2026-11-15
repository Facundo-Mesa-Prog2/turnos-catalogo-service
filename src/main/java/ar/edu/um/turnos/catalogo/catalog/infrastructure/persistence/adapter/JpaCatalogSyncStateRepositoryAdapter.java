package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncState;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSyncStateRepository;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.CatalogSyncStateEntity;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository.JpaCatalogSyncStateRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCatalogSyncStateRepositoryAdapter implements CatalogSyncStateRepository {

    private static final Long SINGLE_ROW_ID = 1L;

    private final JpaCatalogSyncStateRepository jpaRepository;

    @Override
    public Optional<CatalogSyncState> find() {
        return jpaRepository
            .findById(SINGLE_ROW_ID)
            .map(entity ->
                CatalogSyncState.builder().snapshotVersion(entity.getSnapshotVersion()).lastSyncedAt(entity.getLastSyncedAt()).build()
            );
    }

    @Override
    public CatalogSyncState save(CatalogSyncState state) {
        CatalogSyncStateEntity entity = CatalogSyncStateEntity.builder()
            .id(SINGLE_ROW_ID)
            .snapshotVersion(state.getSnapshotVersion())
            .lastSyncedAt(state.getLastSyncedAt())
            .build();

        CatalogSyncStateEntity saved = jpaRepository.save(entity);

        return CatalogSyncState.builder().snapshotVersion(saved.getSnapshotVersion()).lastSyncedAt(saved.getLastSyncedAt()).build();
    }
}
