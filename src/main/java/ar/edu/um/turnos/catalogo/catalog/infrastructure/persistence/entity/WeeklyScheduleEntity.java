package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "weekly_schedule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScheduleEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long professionalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer slotDurationMinutes;

    @Column(nullable = false)
    private boolean enabled;

    private Instant createdAt;
    private Instant updatedAt;
}
