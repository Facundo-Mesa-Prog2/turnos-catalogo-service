package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalCategoryRepository;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.ProfessionalCategoryEntity;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.mapper.ProfessionalCategoryMapper;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalCategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaProfessionalCategoryRepositoryAdapter implements ProfessionalCategoryRepository {

    private final JpaProfessionalCategoryRepository jpaRepository;
    private final ProfessionalCategoryMapper mapper;

    @Override
    public ProfessionalCategory save(ProfessionalCategory category) {
        ProfessionalCategoryEntity entity = mapper.toEntity(category);
        ProfessionalCategoryEntity saved = jpaRepository.save(entity);
        return mapper.toDomainModel(saved);
    }

    @Override
    public Optional<ProfessionalCategory> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomainModel);
    }

    @Override
    public List<ProfessionalCategory> findAllEnabled() {
        return jpaRepository.findByEnabledTrueOrderByNameAsc().stream().map(mapper::toDomainModel).collect(Collectors.toList());
    }
}
