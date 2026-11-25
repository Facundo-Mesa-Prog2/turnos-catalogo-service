package ar.edu.um.turnos.catalogo.catalog.domain.ports.out;

import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import java.util.List;

public interface WeeklyScheduleRepository {
    WeeklySchedule save(WeeklySchedule schedule);
    List<WeeklySchedule> findEnabledByProfessionalId(Long professionalId);
}
