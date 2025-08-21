package fieg.modulos.estabelecimentocielo.repository;

import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.estabelecimentocielo.enums.MeioPagamentoEstabelecimentoCielo;
import fieg.modulos.estabelecimentocielo.model.EstabelecimentoCielo;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class EstabelecimentoCieloRepositoryImpl
        implements EstabelecimentoCieloRepository, PanacheRepositoryBase<EstabelecimentoCielo, Integer> {

    @Override
    public Optional<EstabelecimentoCielo> getByEntidadeAndMeioPagamento(
            Entidade entidade,
            MeioPagamentoEstabelecimentoCielo meioPagamento
    ) {
        return find("entidade = ?1 AND meioPagamento = ?2", entidade.codigo, meioPagamento).singleResultOptional();
    }
}
