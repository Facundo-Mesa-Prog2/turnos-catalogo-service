package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private long totalElements;
    private int page;
    private int size;
    private int totalPages;
}
