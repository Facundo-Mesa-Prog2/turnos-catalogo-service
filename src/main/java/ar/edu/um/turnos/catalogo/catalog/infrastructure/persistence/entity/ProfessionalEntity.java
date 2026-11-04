package ar.edu.um.turnos.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "professional")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private boolean enabled;

    private Instant createdAt;
    private Instant updatedAt;
}
