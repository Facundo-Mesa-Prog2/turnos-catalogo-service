package ar.edu.um.turnos.catalogo.catalog.domain.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogChangeSet {

    private Long version;
    private List<Long> categoryIds;
    private List<Long> professionalIds;
    private List<Long> scheduleIds;
}
