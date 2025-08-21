package fieg.modulos.unidade.repository;

import fieg.modulos.unidade.model.UnidadeCR5;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
class UnidadeCR5RepositoryImpl implements UnidadeCR5Repository, PanacheRepositoryBase<UnidadeCR5, Integer> {

    // No momento são apenas 95, então é ok buscar todas
    @Override
    public List<UnidadeCR5> getAll() {
        return listAll(Sort.by("codigoUnidade"));
    }

    // Tem que ser público para funcionar o cache
    @CacheResult(cacheName = "cr5WebservicesV2-UnidadeCR5RepositoryImpl-getAllIds-cache")
    public List<Integer> getAllIds() {
        return getAll().stream().map(UnidadeCR5::getId).toList();
    }

}
