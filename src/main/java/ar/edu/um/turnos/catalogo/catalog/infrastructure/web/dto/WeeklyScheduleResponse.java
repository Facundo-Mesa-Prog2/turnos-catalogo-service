package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScheduleResponse {

    private Long id;
    private Long professionalId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private Integer slotDurationMinutes;
}
