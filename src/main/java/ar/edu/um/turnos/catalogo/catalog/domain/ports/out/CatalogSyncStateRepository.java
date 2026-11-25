package ar.edu.um.turnos.catalogo.catalog.domain.ports.out;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncState;
import java.util.Optional;

public interface CatalogSyncStateRepository {
    Optional<CatalogSyncState> find();
    CatalogSyncState save(CatalogSyncState state);
}
