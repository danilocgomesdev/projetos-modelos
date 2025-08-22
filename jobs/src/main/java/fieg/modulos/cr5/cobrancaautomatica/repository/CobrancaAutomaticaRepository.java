package fieg.modulos.cr5.cobrancaautomatica.repository;

import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaGestorDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.GestorDTO;
import fieg.modulos.cr5.dto.AgrupamentoCobrancaAutomaticaDTO;
import fieg.modulos.cr5.dto.AgrupamentoCobrancaAutomaticaEmRedeDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailCobrancaAtumoticaDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaDTO;

import java.util.Date;
import java.util.List;

public interface CobrancaAutomaticaRepository {

    List<AgrupamentoCobrancaAutomaticaEmRedeDTO> obterAgrupamentoCobrancaAutomaticaEmRede();

    List<AgrupamentoCobrancaAutomaticaEmRedeDTO> obterAgrupamentoCobrancaAutomatica();

    List<DadosEmailCobrancaAtumoticaDTO> obterDadosEmailCobrancaAtumoticaSimples();

    List<DadosEmailCobrancaAtumoticaDTO> obterDadosEmailCobrancaAtumoticaAgrupadas();

    void salvarStatusEnvioCobrancaAutomatica(Integer idCobrancaAutomatica, Integer enviado, String motivoFalha, Date dataEnvio);

    List<DadosEmailNotificacaoCobrancaAtumoticaDTO> obterDadosEmailNotificacaoCobrancaAtumotica();

    List<AgrupamentoCobrancaAutomaticaDTO> obterAgrupamentoCobrancaAutomatica(List<Integer> listaIdInterface);

    List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> obterDadosNotificacaoGestor();

    List<GestorDTO> obterDadosDoGestor(Integer idUnidade);
}
