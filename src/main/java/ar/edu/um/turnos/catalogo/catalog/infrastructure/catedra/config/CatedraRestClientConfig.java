package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class CatedraRestClientConfig {

    @Bean
    public RestClient catedraRestClient(@Value("${catedra.base-url}") String baseUrl, @Value("${catedra.jwt}") String jwt) {
        return RestClient.builder().baseUrl(baseUrl).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).build();
    }
}
