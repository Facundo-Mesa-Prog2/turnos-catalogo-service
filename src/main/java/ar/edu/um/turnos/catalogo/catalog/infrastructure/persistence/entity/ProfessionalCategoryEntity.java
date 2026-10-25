package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "professional_category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalCategoryEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean enabled;

    private Instant createdAt;
    private Instant updatedAt;
}
