package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangesResponse {

    private Long version;
    private Changes changes;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Changes {

        private List<Long> professionalCategories;
        private List<Long> professionals;
        private List<Long> weeklySchedules;
    }
}
