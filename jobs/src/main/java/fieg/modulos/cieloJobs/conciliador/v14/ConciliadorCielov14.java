
package fieg.modulos.cieloJobs.conciliador.v14;

import fieg.core.exceptions.NegocioException;
import fieg.modulos.cieloJobs.arquivo.*;
import fieg.modulos.cieloJobs.conciliador.ConciliadorService;
import fieg.modulos.cieloJobs.conciliador.IConciliadorCielo;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import fieg.modulos.cieloJobs.dto.DetalheComprovanteVendaCieloDTO;
import fieg.modulos.cieloJobs.mapper.UserMapper;
import fieg.modulos.cr5.dto.ArquivoCieloPagamentoDTO;
import fieg.modulos.cr5.dto.ArquivoCieloVendaDTO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ApplicationScoped
public class ConciliadorCielov14 implements IConciliadorCielo {

    @Inject
    ConciliadorService conciliadorService;

    @Inject
    ConciliacaoPagamentoBC conciliacaoPagamento;

    @Inject
    ConciliacaoVendaBC conciliacaoVenda;

    @Inject
    UserMapper userMapper;

    @Override
    public boolean seAplicaAoArquivo(CabecalhoDTO cabecalhoDTO) {
        return cabecalhoDTO.getVersaoLayout().equals("014");
    }

    @Override
    public void realizarConciliacao(CabecalhoEArquivo cabecalhoEArquivo) {
        ArquivoRetornoDTO arquivoRetornoDTO;
        try {
            arquivoRetornoDTO = new ArquivoRetornoDTO(cabecalhoEArquivo);
        } catch (Exception e) {
            throw new NegocioException("Arquivo " + cabecalhoEArquivo.getNomeArquivo() + " não pode ser lido", e);
        }

        registrarConciliacao(arquivoRetornoDTO);
    }

    private void registrarConciliacao(ArquivoRetornoDTO arquivoRetornoDTO) {
        if (arquivoRetornoDTO.getResumosOperacoes().isEmpty()) {
            return;
        }
        if (arquivoRetornoDTO.getComprovantesVendaTef().isEmpty()) {
            return;
        }

        // salva no banco de dados - via cr5-webservices
        Integer idArquivoCielo = salvar(arquivoRetornoDTO);

        arquivoRetornoDTO.setIdArquivoCielo(idArquivoCielo);

        switch (arquivoRetornoDTO.getCabecalho().getOpcaoExtrato()) {
            case "03" -> conciliacaoVenda.registrarConciliacao(arquivoRetornoDTO);
            case "04" -> conciliacaoPagamento.registrarConciliacao(arquivoRetornoDTO);
            default -> {
                String msg = "Arquivo do tipo " + arquivoRetornoDTO.getCabecalho().getOpcaoExtrato() + " não processado.";
                Log.info(msg);
            }
        }
        salvarVendaNaoConfirmada(arquivoRetornoDTO.getResumosOperacoesPOS(), arquivoRetornoDTO.isArquivoPagamento());
    }

    private Integer salvar(ArquivoRetornoDTO arquivo) {
        Integer idArquivoCielo;

        if (arquivo.isArquivoVenda()) {
            ArquivoCieloVendaDTO arquivoCieloVendaDTO = new ArquivoCieloVendaDTO();
            arquivoCieloVendaDTO.cabecalho = userMapper.cabecalhoArquivoVenda(arquivo.getCabecalho());
            arquivoCieloVendaDTO.nomeArquivo = arquivo.getNomeArquivo();
            arquivoCieloVendaDTO.comprovantesVenda = userMapper.detalheComprovanteVenda(arquivo.getComprovantesVenda());
            arquivoCieloVendaDTO.detalhesResumoOperacao = userMapper.detalheResumoOperacaoVenda(arquivo.getDetalhesResumoOperacao());

            idArquivoCielo = conciliadorService.enviarPostConverterCodigoArquivoVenda(arquivoCieloVendaDTO);
        } else {
            ArquivoCieloPagamentoDTO arquivoCieloPagamentoDTO = new ArquivoCieloPagamentoDTO();
            arquivoCieloPagamentoDTO.cabecalho = userMapper.cabecalhoArquivoPagamento(arquivo.getCabecalho());
            arquivoCieloPagamentoDTO.comprovantesVenda = userMapper.detalheComprovantePagamentos(arquivo.getComprovantesVenda());
            arquivoCieloPagamentoDTO.detalhesResumoOperacao = userMapper.detalheResumoOperacaoPagamentos(arquivo.getDetalhesResumoOperacao());

            idArquivoCielo = conciliadorService.enviarPostConverterCodigoArquivoPagamento(arquivoCieloPagamentoDTO);
        }
        return idArquivoCielo;
    }

    private void salvarVendaNaoConfirmada(Set<ResumoOperacaoDTO> resumosOperacoesPOS, boolean arquivoPagamento) {
        List<DetalheComprovanteVendaDTO> vendasPOS = new ArrayList<>();
        for (ResumoOperacaoDTO resumoOperacaoDTO : resumosOperacoesPOS) {
            if (resumoOperacaoDTO.getDetalhesComprovantesVenda() == null) {
                continue;
            }
            for (DetalheComprovanteVendaDTO dv : resumoOperacaoDTO.getDetalhesComprovantesVenda()) {
                DetalheResumoOperacaoDTO resumo = resumoOperacaoDTO.getDetalhesResumoOperacao().get(0);
                dv.setDataPrevistaPagamento(resumo.getDataPrevistaPagamento());
            }
            vendasPOS.addAll(resumoOperacaoDTO.getDetalhesComprovantesVenda());
        }

        //Evita duplicação de registro
        Set<DetalheComprovanteVendaCieloDTO> setVendasDTO = new HashSet<>();

        for (DetalheComprovanteVendaDTO detalheCV : vendasPOS) {
            DetalheComprovanteVendaCieloDTO dto = new DetalheComprovanteVendaCieloDTO(detalheCV, arquivoPagamento);
            setVendasDTO.add(dto);
        }

        for (DetalheComprovanteVendaCieloDTO dto : setVendasDTO) {
            conciliadorService.consumirServicoVendaPOS(dto);
        }
    }

}
