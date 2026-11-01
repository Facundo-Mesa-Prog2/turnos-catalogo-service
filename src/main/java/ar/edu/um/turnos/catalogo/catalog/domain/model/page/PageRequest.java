package ar.edu.um.turnos.catalogo.catalog.domain.model.page;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    private int page;
    private int size;
}
