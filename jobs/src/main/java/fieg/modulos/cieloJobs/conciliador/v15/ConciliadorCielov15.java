package fieg.modulos.cieloJobs.conciliador.v15;

import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoPagamentoCielov15;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoRetornoCielov15;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoVendaCielov15;
import fieg.modulos.cieloJobs.conciliador.IConciliadorCielo;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import fieg.modulos.cr5.services.ArquivoCielov15Service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ConciliadorCielov15 implements IConciliadorCielo {

    @Inject
    ConciliacaoPagamentov15 conciliacaoPagamentov15;

    @Inject
    ConciliacaoVendav15 conciliacaoVendav15;

    @Inject
    LeitorArquivoCielov15 leitorArquivoCielov15;

    @Inject
    ArquivoCielov15Service arquivoCielov15Service;

    @Override
    public boolean seAplicaAoArquivo(CabecalhoDTO cabecalhoDTO) {
        return cabecalhoDTO.getVersaoLayout().equals("015");
    }

    @Override
    public void realizarConciliacao(CabecalhoEArquivo cabecalhoEArquivo) {
        ArquivoRetornoCielov15 arquivo = leitorArquivoCielov15.leArquivo(cabecalhoEArquivo);

        if (arquivo instanceof ArquivoVendaCielov15 venda) {
            arquivoCielov15Service.salvarArquivoVendaCielo(venda);
            conciliacaoVendav15.conciliarVenda(venda);
        } else if (arquivo instanceof ArquivoPagamentoCielov15 pagamento) {
            arquivoCielov15Service.salvarArquivoPagamentoCielo(pagamento);
            conciliacaoPagamentov15.conciliarPagamento(pagamento);
        }
    }

}
