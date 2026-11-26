package ar.edu.um.turnos.catalogo.catalog.domain.ports.out;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSnapshot;

public interface CatalogSnapshotPort {
    CatalogSnapshot fetchSnapshot();
}
