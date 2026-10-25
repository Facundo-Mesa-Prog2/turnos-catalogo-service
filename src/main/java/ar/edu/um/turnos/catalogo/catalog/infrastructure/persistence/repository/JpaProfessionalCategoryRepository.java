package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.ProfessionalCategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfessionalCategoryRepository extends JpaRepository<ProfessionalCategoryEntity, Long> {
    List<ProfessionalCategoryEntity> findByEnabledTrueOrderByNameAsc();
}
