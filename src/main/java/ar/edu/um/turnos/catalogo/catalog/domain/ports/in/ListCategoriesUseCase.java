package ar.edu.um.turnos.catalogo.catalog.domain.ports.in;

import ar.edu.um.turnos.catalogo.catalog.domain.model.ProfessionalCategory;
import java.util.List;

public interface ListCategoriesUseCase {
    List<ProfessionalCategory> listCategories();
}
