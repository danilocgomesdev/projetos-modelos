package fieg.modulos.estabelecimentocielo.repository;

import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.estabelecimentocielo.enums.MeioPagamentoEstabelecimentoCielo;
import fieg.modulos.estabelecimentocielo.model.EstabelecimentoCielo;

import java.util.Optional;

public interface EstabelecimentoCieloRepository {

    Optional<EstabelecimentoCielo> getByEntidadeAndMeioPagamento(
            Entidade entidade,
            MeioPagamentoEstabelecimentoCielo meioPagamentoEstabelecimentoCielo
    );
}
