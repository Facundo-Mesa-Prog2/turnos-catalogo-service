package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotResponse {

    private Long snapshotVersion;
    private String generatedAt;
    private List<CategoryDto> professionalCategories;
    private List<ProfessionalDto> professionals;
    private List<ScheduleDto> weeklySchedules;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryDto {

        private Long id;
        private String name;
        private String description;
        private Boolean enabled;
        private String createdAt;
        private String updatedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProfessionalDto {

        private Long id;
        private Long categoryId;
        private String firstName;
        private String lastName;
        private Boolean enabled;
        private String createdAt;
        private String updatedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ScheduleDto {

        private Long id;
        private Long professionalId;
        private String dayOfWeek;
        private String startTime;
        private String endTime;
        private Integer slotDurationMinutes;
        private Boolean enabled;
        private String createdAt;
        private String updatedAt;
    }
}
