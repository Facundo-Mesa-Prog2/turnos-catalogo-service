package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSnapshot;
import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogSnapshotPort;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto.SnapshotResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.mapper.CatedraDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CatedraCatalogSnapshotAdapter implements CatalogSnapshotPort {

    private final RestClient catedraRestClient;
    private final CatedraDtoMapper mapper;

    @Override
    public CatalogSnapshot fetchSnapshot() {
        SnapshotResponse response = catedraRestClient.get().uri("/api/synchronization/snapshot").retrieve().body(SnapshotResponse.class);

        if (response == null) {
            throw new IllegalStateException("La catedra devolvio un snapshot vacio");
        }

        return CatalogSnapshot.builder()
            .snapshotVersion(response.getSnapshotVersion())
            .categories(toCategories(response.getProfessionalCategories()))
            .professionals(toProfessionals(response.getProfessionals()))
            .schedules(toSchedules(response.getWeeklySchedules()))
            .build();
    }

    private List<ProfessionalCategory> toCategories(List<SnapshotResponse.CategoryDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    private List<Professional> toProfessionals(List<SnapshotResponse.ProfessionalDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    private List<WeeklySchedule> toSchedules(List<SnapshotResponse.ScheduleDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}
