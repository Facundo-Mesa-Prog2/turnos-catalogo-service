package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.mapper.ProfessionalMapper;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaProfessionalRepositoryAdapter implements ProfessionalRepository {

    private final JpaProfessionalRepository jpaRepository;
    private final ProfessionalMapper mapper;

    @Override
    public Professional save(Professional professional) {
        ProfessionalEntity entity = mapper.toEntity(professional);
        ProfessionalEntity saved = jpaRepository.save(entity);
        return mapper.toDomainModel(saved);
    }

    @Override
    public Optional<Professional> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomainModel);
    }

    @Override
    public PageResult<Professional> search(Long categoryId, String text, PageRequest pageRequest) {
        // PageRequest de Spring, distinto del PageRequest del dominio
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        Page<ProfessionalEntity> page = jpaRepository.search(categoryId, text, pageable);

        List<Professional> content = page.getContent().stream().map(mapper::toDomainModel).collect(Collectors.toList());

        return PageResult.<Professional>builder()
            .content(content)
            .totalElements(page.getTotalElements())
            .page(pageRequest.getPage())
            .size(pageRequest.getSize())
            .build();
    }
}
