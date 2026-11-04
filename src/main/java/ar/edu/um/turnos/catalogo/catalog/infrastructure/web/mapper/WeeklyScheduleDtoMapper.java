package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.mapper;

import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto.WeeklyScheduleResponse;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class WeeklyScheduleDtoMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public WeeklyScheduleResponse toResponse(WeeklySchedule schedule) {
        if (schedule == null) {
            return null;
        }
        return WeeklyScheduleResponse.builder()
            .id(schedule.getId())
            .professionalId(schedule.getProfessionalId())
            .dayOfWeek(schedule.getDayOfWeek().name())
            .startTime(schedule.getStartTime().format(TIME_FORMATTER))
            .endTime(schedule.getEndTime().format(TIME_FORMATTER))
            .slotDurationMinutes(schedule.getSlotDurationMinutes())
            .build();
    }
}
