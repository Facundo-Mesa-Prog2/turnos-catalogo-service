package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.GetProfessionalScheduleUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.WeeklyScheduleRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProfessionalScheduleUseCaseImpl implements GetProfessionalScheduleUseCase {

    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final ProfessionalRepository professionalRepository;

    @Override
    public List<WeeklySchedule> getProfessionalSchedule(Long professionalId) {
        boolean professionalIsAvailable = professionalRepository.findById(professionalId).filter(Professional::isEnabled).isPresent();

        if (!professionalIsAvailable) {
            return Collections.emptyList();
        }

        return weeklyScheduleRepository.findEnabledByProfessionalId(professionalId);
    }
}
