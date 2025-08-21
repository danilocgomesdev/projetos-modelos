package fieg.modulos.cobrancaautomatica.service;

import fieg.modulos.cobrancaautomatica.dto.DadosEmailCobrancaMultipartDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaGestorDTO;

import java.util.List;

public interface CobrancaAutomaticaService {

    void enviarEmailCobrancaAutomatica(DadosEmailCobrancaMultipartDTO dto);

    void enviarEmailCobrancaAutomaticaAgrupada(DadosEmailCobrancaMultipartDTO dto);

    void enviarEmailNotificaoCobrancaAutomatica(DadosEmailNotificacaoCobrancaAtumoticaDTO dto);

    void enviarEmailNotificaoCobrancaAutomaticaGestor(List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> dto);
}
