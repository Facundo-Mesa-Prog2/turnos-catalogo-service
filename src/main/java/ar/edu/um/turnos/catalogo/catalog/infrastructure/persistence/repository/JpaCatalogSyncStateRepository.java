package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.CatalogSyncStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCatalogSyncStateRepository extends JpaRepository<CatalogSyncStateEntity, Long> {}
