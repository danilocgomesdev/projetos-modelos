package fieg.core.health;

import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import java.util.UUID;

@Readiness
@ApplicationScoped
public class RedisReadyHealthCheck implements HealthCheck {

    private static final String HEALTHCHECK_NAME = "Redis working";

    @ConfigProperty(name = "quarkus.application.name")
    String artifactId;

    private final ValueCommands<String, String> countCommands;
    private final KeyCommands<String> keyCommands;

    @Inject
    public RedisReadyHealthCheck(RedisDataSource redisDataSource) {
        countCommands = redisDataSource.value(String.class);
        keyCommands = redisDataSource.key();
    }

    @Override
    public HealthCheckResponse call() {
        final String uuid = UUID.randomUUID().toString();
        final String key = "Teste:" + artifactId + ":" + uuid;

        try {
            countCommands.set(key, uuid);

            final String result = countCommands.get(key);

            if (!uuid.equals(result)) {
                throw new RuntimeException("Redis not ready");
            }

            keyCommands.del(key);

            return HealthCheckResponse
                    .builder()
                    .name(HEALTHCHECK_NAME)
                    .up()
                    .build();
        } catch (RuntimeException e) {
            Log.error("Error on Ready healthcheck", e);

            return HealthCheckResponse
                    .builder()
                    .withData("error", e.getMessage())
                    .name(HEALTHCHECK_NAME)
                    .down()
                    .build();
        }
    }
}
