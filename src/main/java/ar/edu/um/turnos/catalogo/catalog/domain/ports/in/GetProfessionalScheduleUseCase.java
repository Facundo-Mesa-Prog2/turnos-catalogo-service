package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import java.util.List;

public interface GetProfessionalScheduleUseCase {
    List<WeeklySchedule> getProfessionalSchedule(Long professionalId);
}
