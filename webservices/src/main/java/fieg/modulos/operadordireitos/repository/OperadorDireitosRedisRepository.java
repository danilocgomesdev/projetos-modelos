package fieg.modulos.operadordireitos.repository;

import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import fieg.modulos.operadordireitos.dto.OperadorDireitoFilterDTO;
import fieg.modulos.operadordireitos.dto.OperadorDireitosCacheDTO;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.value.SetArgs;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
class OperadorDireitosRedisRepository implements OperadorDireitosRepository {

    private final ValueCommands<OperadorDireitoFilterDTO, OperadorDireitosCacheDTO> operadorDireitoCommands;
    private final KeyCommands<OperadorDireitoFilterDTO> keyCommands;

    @Inject
    public OperadorDireitosRedisRepository(RedisDataSource redisDataSource) {
        this.operadorDireitoCommands = redisDataSource.value(OperadorDireitoFilterDTO.class, OperadorDireitosCacheDTO.class);
        this.keyCommands = redisDataSource.key(OperadorDireitoFilterDTO.class);
    }

    @Override
    public void set(OperadorDireitoFilterDTO filterDTO, List<OperadorDireitoDTO> direitos, Long expiresAt) {
        var cache = new OperadorDireitosCacheDTO();
        cache.setDireitos(direitos);
        operadorDireitoCommands.set(filterDTO, cache, setArgsPadrao(expiresAt));
    }

    @Override
    public List<OperadorDireitoDTO> get(OperadorDireitoFilterDTO filterDTO) {
        var cache = operadorDireitoCommands.get(filterDTO);
        if (cache == null) {
            return new ArrayList<>();
        }

        return cache.getDireitos();
    }

    @Override
    public boolean exists(OperadorDireitoFilterDTO filterDTO) {
        return keyCommands.exists(filterDTO);
    }

    private SetArgs setArgsPadrao(Long expiresAt) {
        return new SetArgs().exAt(expiresAt);
    }
}
