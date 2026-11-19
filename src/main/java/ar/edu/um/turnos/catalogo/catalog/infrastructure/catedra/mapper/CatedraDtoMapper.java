package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.mapper;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto.SnapshotResponse;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class CatedraDtoMapper {

    public ProfessionalCategory toDomain(SnapshotResponse.CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        return ProfessionalCategory.builder()
            .id(dto.getId())
            .name(dto.getName())
            .description(dto.getDescription())
            .enabled(Boolean.TRUE.equals(dto.getEnabled()))
            .createdAt(parseInstant(dto.getCreatedAt()))
            .updatedAt(parseInstant(dto.getUpdatedAt()))
            .build();
    }

    public Professional toDomain(SnapshotResponse.ProfessionalDto dto) {
        if (dto == null) {
            return null;
        }
        return Professional.builder()
            .id(dto.getId())
            .categoryId(dto.getCategoryId())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .enabled(Boolean.TRUE.equals(dto.getEnabled()))
            .createdAt(parseInstant(dto.getCreatedAt()))
            .updatedAt(parseInstant(dto.getUpdatedAt()))
            .build();
    }

    public WeeklySchedule toDomain(SnapshotResponse.ScheduleDto dto) {
        if (dto == null) {
            return null;
        }
        return WeeklySchedule.builder()
            .id(dto.getId())
            .professionalId(dto.getProfessionalId())
            .dayOfWeek(DayOfWeek.valueOf(dto.getDayOfWeek()))
            .startTime(LocalTime.parse(dto.getStartTime()))
            .endTime(LocalTime.parse(dto.getEndTime()))
            .slotDurationMinutes(dto.getSlotDurationMinutes())
            .enabled(Boolean.TRUE.equals(dto.getEnabled()))
            .createdAt(parseInstant(dto.getCreatedAt()))
            .updatedAt(parseInstant(dto.getUpdatedAt()))
            .build();
    }

    private Instant parseInstant(String value) {
        return value == null ? null : Instant.parse(value);
    }
}
