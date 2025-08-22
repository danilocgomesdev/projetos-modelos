package fieg.modulos.tarefa;

import fieg.modulos.cr5.services.CobrancaClienteServices;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class IncluiPagamentoEFormaJob {

    @Inject
    CobrancaClienteServices cobrancaClienteServices;


    public void executaJob() {
        cobrancaClienteServices.inserirPagtoeForma();
    }

    public void executaJobGrupo() {
        cobrancaClienteServices.inserirPagtoeFormaGrupo();
    }
}
