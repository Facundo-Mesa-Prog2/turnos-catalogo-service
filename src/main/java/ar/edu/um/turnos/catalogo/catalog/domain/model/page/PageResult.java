package ar.edu.um.turnos.catalogo.catalog.domain.model.page;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private List<T> content;
    private long totalElements;
    private int page;
    private int size;
}
