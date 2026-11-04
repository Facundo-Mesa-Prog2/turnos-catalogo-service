package ar.edu.um.turnos.catalogo.catalog.infrastructure.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Long categoryId;
    private String categoryName;
}
