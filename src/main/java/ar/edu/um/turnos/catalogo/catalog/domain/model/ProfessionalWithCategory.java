package ar.edu.um.turnos.catalogo.catalog.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalWithCategory {

    private Professional professional;
    private ProfessionalCategory category;
}
