package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
    @Query(
        """
        select p from ProfessionalEntity p
        where p.enabled = true
          and (:categoryId is null or p.categoryId = :categoryId)
          and (cast(:text as string) is null
               or lower(p.firstName) like lower(concat('%', cast(:text as string), '%'))
               or lower(p.lastName) like lower(concat('%', cast(:text as string), '%')))
        order by p.lastName asc, p.firstName asc
        """
    )
    Page<ProfessionalEntity> search(@Param("categoryId") Long categoryId, @Param("text") String text, Pageable pageable);
}
