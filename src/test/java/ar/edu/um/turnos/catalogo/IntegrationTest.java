package ar.edu.um.turnos.catalogo;

import ar.edu.um.turnos.catalogo.config.AsyncSyncConfiguration;
import ar.edu.um.turnos.catalogo.config.EmbeddedSQL;
import ar.edu.um.turnos.catalogo.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { TurnosCatalogoServiceApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
