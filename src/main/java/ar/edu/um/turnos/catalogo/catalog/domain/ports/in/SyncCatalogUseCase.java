package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncResult;

public interface SyncCatalogUseCase {
    CatalogSyncResult syncCatalog();
}
