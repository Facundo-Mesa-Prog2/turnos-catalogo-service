package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.WeeklyScheduleRepository;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.mapper.WeeklyScheduleMapper;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaWeeklyScheduleRepositoryAdapter implements WeeklyScheduleRepository {

    private final JpaWeeklyScheduleRepository jpaRepository;
    private final WeeklyScheduleMapper mapper;

    @Override
    public WeeklySchedule save(WeeklySchedule schedule) {
        WeeklyScheduleEntity entity = mapper.toEntity(schedule);
        WeeklyScheduleEntity saved = jpaRepository.save(entity);
        return mapper.toDomainModel(saved);
    }

    @Override
    public List<WeeklySchedule> findEnabledByProfessionalId(Long professionalId) {
        return jpaRepository
            .findByProfessionalIdAndEnabledTrueOrderByDayOfWeekAscStartTimeAsc(professionalId)
            .stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }
}
