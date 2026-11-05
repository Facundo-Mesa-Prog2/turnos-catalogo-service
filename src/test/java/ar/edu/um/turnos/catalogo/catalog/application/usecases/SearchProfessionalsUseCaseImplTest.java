package ar.edu.um.turnos.catalogo.catalog.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.turnos.catalogo.catalog.domain.model.Professional;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageRequest;
import ar.edu.um.turnos.catalogo.catalog.domain.model.page.PageResult;
import ar.edu.um.turnos.catalogo.catalog.domain.ports.out.ProfessionalRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchProfessionalsUseCaseImplTest {

    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private SearchProfessionalsUseCaseImpl useCase;

    @Test
    @DisplayName("convierte el texto vacio en null para no filtrar por nombre")
    void textoVacioSeConvierteEnNull() {
        PageRequest pageRequest = PageRequest.builder().page(0).size(20).build();
        when(professionalRepository.search(any(), any(), any())).thenReturn(PageResult.<Professional>builder().content(List.of()).build());

        useCase.searchProfessionals(101L, "   ", pageRequest);

        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        verify(professionalRepository).search(eq(101L), textCaptor.capture(), eq(pageRequest));
        assertThat(textCaptor.getValue()).isNull();
    }

    @Test
    @DisplayName("quita los espacios sobrantes del texto de busqueda")
    void textoSeRecorta() {
        PageRequest pageRequest = PageRequest.builder().page(0).size(20).build();
        when(professionalRepository.search(any(), any(), any())).thenReturn(PageResult.<Professional>builder().content(List.of()).build());

        useCase.searchProfessionals(null, "  torres  ", pageRequest);

        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        verify(professionalRepository).search(eq(null), textCaptor.capture(), eq(pageRequest));
        assertThat(textCaptor.getValue()).isEqualTo("torres");
    }
}
