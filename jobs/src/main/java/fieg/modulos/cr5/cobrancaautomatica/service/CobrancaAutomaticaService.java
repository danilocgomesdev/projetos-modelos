package fieg.modulos.cr5.cobrancaautomatica.service;

public interface CobrancaAutomaticaService {

    void agruparTodasCobrancasAutomaticasEmRede();

    void agruparTodasCobrancasAutomaticas();

    void enviarEmailCobrancaAutomaticaSimples();

    void enviarEmailCobrancaAutomaticaAgrupada();

    void enviarEmailNotificacaoCobrancaAutomatica();

    void enviarEmailNotificacaoCobrancaAutomaticaGestor();
}
