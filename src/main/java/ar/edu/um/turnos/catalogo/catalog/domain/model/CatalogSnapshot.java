package ar.edu.um.turnos.catalogo.catalog.domain.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSnapshot {

    private Long snapshotVersion;
    private List<ProfessionalCategory> categories;
    private List<Professional> professionals;
    private List<WeeklySchedule> schedules;
}
