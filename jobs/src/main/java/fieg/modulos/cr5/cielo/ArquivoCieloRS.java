
package fieg.modulos.cr5.cielo;


import fieg.modulos.cr5.services.ArquivoCieloNegocio;
import fieg.modulos.cr5.dto.ArquivoCieloPagamentoDTO;
import fieg.modulos.cr5.dto.ArquivoCieloVendaDTO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ArquivoCieloRS {

    @Inject
    ArquivoCieloNegocio arquivoCieloNegocio;

    public Integer salvarArquivoVenda(ArquivoCieloVendaDTO param) {
        Integer id = arquivoCieloNegocio.salvar(param);
        Log.infof("ArquivoCieloVenda salvo. Id: %d", id);
        return id;
    }

    public Integer salvarArquivoPagamento(ArquivoCieloPagamentoDTO param) {
        Integer id = arquivoCieloNegocio.salvar(param);
        Log.infof("ArquivoCieloPagamento salvo. Id: %d", id);
        return id;
    }
}
