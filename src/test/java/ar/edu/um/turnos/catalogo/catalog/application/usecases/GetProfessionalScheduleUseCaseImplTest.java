package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.WeeklyScheduleRepository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetProfessionalScheduleUseCaseImplTest {

    @Mock
    private WeeklyScheduleRepository weeklyScheduleRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private GetProfessionalScheduleUseCaseImpl useCase;

    @Test
    @DisplayName("devuelve la agenda de un profesional habilitado")
    void profesionalHabilitadoDevuelveAgenda() {
        Professional professional = Professional.builder().id(201L).enabled(true).build();
        WeeklySchedule schedule = WeeklySchedule.builder()
            .id(301L)
            .professionalId(201L)
            .dayOfWeek(DayOfWeek.MONDAY)
            .startTime(LocalTime.of(12, 0))
            .endTime(LocalTime.of(15, 0))
            .slotDurationMinutes(30)
            .enabled(true)
            .build();

        when(professionalRepository.findById(201L)).thenReturn(Optional.of(professional));
        when(weeklyScheduleRepository.findEnabledByProfessionalId(201L)).thenReturn(List.of(schedule));

        List<WeeklySchedule> result = useCase.getProfessionalSchedule(201L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
    }

    @Test
    @DisplayName("no devuelve agenda si el profesional esta deshabilitado")
    void profesionalDeshabilitadoNoDevuelveAgenda() {
        Professional professional = Professional.builder().id(201L).enabled(false).build();
        when(professionalRepository.findById(201L)).thenReturn(Optional.of(professional));

        List<WeeklySchedule> result = useCase.getProfessionalSchedule(201L);

        assertThat(result).isEmpty();
        verify(weeklyScheduleRepository, never()).findEnabledByProfessionalId(201L);
    }

    @Test
    @DisplayName("no devuelve agenda si el profesional no existe")
    void profesionalInexistenteNoDevuelveAgenda() {
        when(professionalRepository.findById(999L)).thenReturn(Optional.empty());

        List<WeeklySchedule> result = useCase.getProfessionalSchedule(999L);

        assertThat(result).isEmpty();
    }
}
