package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.adapter;

import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogChangeSet;
import ar.edu.um.turnos.catalogo.catalog.domain.model.CatalogSyncMetadata;
import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.CatalogChangesPort;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto.ChangesResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.dto.SnapshotResponse;
import ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.mapper.CatedraDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCatalogChangesAdapter implements CatalogChangesPort {

    private static final String METADATA_KEY = "catedra:sync:metadata";
    private static final String CHANGES_KEY_PREFIX = "catedra:sync:changes:";
    private static final String CATEGORIES_KEY = "catedra:sync:professional-categories";
    private static final String PROFESSIONALS_KEY = "catedra:sync:professionals";
    private static final String SCHEDULES_KEY = "catedra:sync:weekly-schedules";

    private final StringRedisTemplate catedraRedisTemplate;
    private final ObjectMapper objectMapper;
    private final CatedraDtoMapper mapper;

    @Override
    public CatalogSyncMetadata fetchMetadata() {
        HashOperations<String, String, String> hashOps = catedraRedisTemplate.opsForHash();

        String current = hashOps.get(METADATA_KEY, "currentVersion");
        String oldest = hashOps.get(METADATA_KEY, "oldestAvailableVersion");

        if (current == null || oldest == null) {
            throw new IllegalStateException("Redis no publico la metadata de sincronizacion");
        }

        return CatalogSyncMetadata.builder().currentVersion(Long.valueOf(current)).oldestAvailableVersion(Long.valueOf(oldest)).build();
    }

    @Override
    public Optional<CatalogChangeSet> fetchChanges(long version) {
        String json = catedraRedisTemplate.opsForValue().get(CHANGES_KEY_PREFIX + version);

        if (json == null) {
            return Optional.empty();
        }

        ChangesResponse.Changes changes = readJson(json, ChangesResponse.class).getChanges();

        return Optional.of(
            CatalogChangeSet.builder()
                .version(version)
                .categoryIds(safeList(changes == null ? null : changes.getProfessionalCategories()))
                .professionalIds(safeList(changes == null ? null : changes.getProfessionals()))
                .scheduleIds(safeList(changes == null ? null : changes.getWeeklySchedules()))
                .build()
        );
    }

    @Override
    public List<ProfessionalCategory> fetchCategoriesByIds(Collection<Long> ids) {
        return readEntities(CATEGORIES_KEY, ids, SnapshotResponse.CategoryDto.class)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Professional> fetchProfessionalsByIds(Collection<Long> ids) {
        return readEntities(PROFESSIONALS_KEY, ids, SnapshotResponse.ProfessionalDto.class)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<WeeklySchedule> fetchSchedulesByIds(Collection<Long> ids) {
        return readEntities(SCHEDULES_KEY, ids, SnapshotResponse.ScheduleDto.class)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    private <T> List<T> readEntities(String key, Collection<Long> ids, Class<T> type) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        List<String> fields = ids.stream().map(String::valueOf).collect(Collectors.toList());

        HashOperations<String, String, String> hashOps = catedraRedisTemplate.opsForHash();
        List<String> values = hashOps.multiGet(key, fields);

        List<T> result = new ArrayList<>();
        for (String value : values) {
            if (value != null) {
                result.add(readJson(value, type));
            }
        }
        return result;
    }

    private <T> T readJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo interpretar el JSON publicado por la catedra", e);
        }
    }

    private List<Long> safeList(List<Long> values) {
        return values == null ? List.of() : values;
    }
}
