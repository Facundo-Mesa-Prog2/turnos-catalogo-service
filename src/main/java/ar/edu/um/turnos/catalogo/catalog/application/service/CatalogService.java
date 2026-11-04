package ar.edu.um.turnos.catalogo.catalog.application.service;

import ar.edu.um.turnos.catalogo.catalog.application.usecases.GetProfessionalDetailUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalWithCategory;
import ar.edu.um.turnos.catalogo.catalog.domain.model.WeeklySchedule;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.GetProfessionalScheduleUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.ListCategoriesUseCase;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.in.SearchProfessionalsUseCase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ListCategoriesUseCase listCategoriesUseCase;
    private final SearchProfessionalsUseCase searchProfessionalsUseCase;
    private final GetProfessionalDetailUseCase getProfessionalDetailUseCase;
    private final GetProfessionalScheduleUseCase getProfessionalScheduleUseCase;

    public List<ProfessionalCategory> listCategories() {
        return listCategoriesUseCase.listCategories();
    }

    public PageResult<Professional> searchProfessionals(Long categoryId, String text, PageRequest pageRequest) {
        return searchProfessionalsUseCase.searchProfessionals(categoryId, text, pageRequest);
    }

    public Optional<ProfessionalWithCategory> getProfessionalDetail(Long id) {
        return getProfessionalDetailUseCase.getProfessionalDetail(id);
    }

    public List<WeeklySchedule> getProfessionalSchedule(Long professionalId) {
        return getProfessionalScheduleUseCase.getProfessionalSchedule(professionalId);
    }
}
