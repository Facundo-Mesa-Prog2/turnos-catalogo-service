package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSnapshot;
import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSnapshotPort;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto.SnapshotResponse;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CatedraCatalogSnapshotAdapter implements CatalogSnapshotPort {

    private final RestClient catedraRestClient;

    @Override
    public CatalogSnapshot fetchSnapshot() {
        SnapshotResponse response = catedraRestClient.get().uri("/api/synchronization/snapshot").retrieve().body(SnapshotResponse.class);

        if (response == null) {
            throw new IllegalStateException("La catedra devolvio un snapshot vacio");
        }

        return CatalogSnapshot.builder()
            .snapshotVersion(response.getSnapshotVersion())
            .categories(toCategories(response.getProfessionalCategories()))
            .professionals(toProfessionals(response.getProfessionals()))
            .schedules(toSchedules(response.getWeeklySchedules()))
            .build();
    }

    private List<ProfessionalCategory> toCategories(List<SnapshotResponse.CategoryDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos
            .stream()
            .map(dto ->
                ProfessionalCategory.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .enabled(Boolean.TRUE.equals(dto.getEnabled()))
                    .createdAt(parseInstant(dto.getCreatedAt()))
                    .updatedAt(parseInstant(dto.getUpdatedAt()))
                    .build()
            )
            .collect(Collectors.toList());
    }

    private List<Professional> toProfessionals(List<SnapshotResponse.ProfessionalDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos
            .stream()
            .map(dto ->
                Professional.builder()
                    .id(dto.getId())
                    .categoryId(dto.getCategoryId())
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .enabled(Boolean.TRUE.equals(dto.getEnabled()))
                    .createdAt(parseInstant(dto.getCreatedAt()))
                    .updatedAt(parseInstant(dto.getUpdatedAt()))
                    .build()
            )
            .collect(Collectors.toList());
    }

    private List<WeeklySchedule> toSchedules(List<SnapshotResponse.ScheduleDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos
            .stream()
            .map(dto ->
                WeeklySchedule.builder()
                    .id(dto.getId())
                    .professionalId(dto.getProfessionalId())
                    .dayOfWeek(DayOfWeek.valueOf(dto.getDayOfWeek()))
                    .startTime(LocalTime.parse(dto.getStartTime()))
                    .endTime(LocalTime.parse(dto.getEndTime()))
                    .slotDurationMinutes(dto.getSlotDurationMinutes())
                    .enabled(Boolean.TRUE.equals(dto.getEnabled()))
                    .createdAt(parseInstant(dto.getCreatedAt()))
                    .updatedAt(parseInstant(dto.getUpdatedAt()))
                    .build()
            )
            .collect(Collectors.toList());
    }

    private Instant parseInstant(String value) {
        return value == null ? null : Instant.parse(value);
    }
}
