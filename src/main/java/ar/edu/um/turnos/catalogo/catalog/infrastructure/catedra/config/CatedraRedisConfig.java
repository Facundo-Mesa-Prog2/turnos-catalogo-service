package ar.edu.um.turnos.catalogo.catalog.infrastructure.catedra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CatedraRedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
        @Value("${catedra.redis.host}") String host,
        @Value("${catedra.redis.port}") int port,
        @Value("${catedra.redis.username}") String username,
        @Value("${catedra.redis.password}") String password
    ) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setUsername(username);
        config.setPassword(password);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public StringRedisTemplate catedraRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}
