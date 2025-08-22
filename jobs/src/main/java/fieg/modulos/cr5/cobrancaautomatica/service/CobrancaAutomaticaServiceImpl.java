package fieg.modulos.cr5.cobrancaautomatica.service;

import fieg.core.util.UtilJson;
import fieg.modulos.cr5.cobrancaautomatica.dto.*;
import fieg.modulos.cr5.dto.AgrupamentoCobrancaAutomaticaDTO;
import fieg.modulos.cr5.dto.AgrupamentoCobrancaAutomaticaEmRedeDTO;
import fieg.modulos.cr5.cobrancaautomatica.repository.CobrancaAutomaticaRepository;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import fieg.modulos.emailservices.dto.DadosEmailCobrancaMultipartDTO;
import fieg.modulos.emailservices.restclient.EmailServicesRestClient;
import io.quarkus.logging.Log;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class CobrancaAutomaticaServiceImpl implements CobrancaAutomaticaService {

    private static final int OPERATOR_ID = 8442;

    @Inject
    CobrancaAutomaticaRepository cobrancaAutomaticaRepository;

    @Inject
    @RestClient
    Cr5WebservicesRestClient cr5WebservicesRestClient;

    @Inject
    @RestClient
    EmailServicesRestClient emailServicesRestClient;

    @Override
    public void agruparTodasCobrancasAutomaticasEmRede() {
        List<AgrupamentoCobrancaAutomaticaEmRedeDTO> list = cobrancaAutomaticaRepository.obterAgrupamentoCobrancaAutomaticaEmRede();
        Log.info("Buscando lista de cobranças automáticas para agrupar em REDE: " + UtilJson.toJson(list));
        for (AgrupamentoCobrancaAutomaticaEmRedeDTO dto : list) {
            try {
                Log.info("Lista dos DTOs para agrupar REDE: " + UtilJson.toJson(dto));
                cr5WebservicesRestClient.agruparParcelasProtheus(dto);
            } catch (Exception e) {
                Log.error("Erro ao agrupar parcelas para DTO: " + dto, e);
            }
        }
    }


    @Override
    public void agruparTodasCobrancasAutomaticas() {
        List<AgrupamentoCobrancaAutomaticaEmRedeDTO> list = cobrancaAutomaticaRepository.obterAgrupamentoCobrancaAutomatica();
        Log.info("Buscando lista de cobranças automáticas para agrupar: " + UtilJson.toJson(list));
        for (AgrupamentoCobrancaAutomaticaEmRedeDTO dto : list) {
            try {
               List<AgrupamentoCobrancaAutomaticaDTO> dtoList = cobrancaAutomaticaRepository.obterAgrupamentoCobrancaAutomatica(dto.getInterfacesCobrancas());
                Log.info("Lista dos DTOs para agrupar: " + UtilJson.toJson(dtoList));
               cr5WebservicesRestClient.agruparParcelas(dtoList);
            } catch (Exception e) {
                Log.error("Erro ao agrupar parcelas para DTO: " + dto, e);
            }
        }
    }


    @SneakyThrows
    private DadosEmailCobrancaMultipartDTO montarEmailCobranca(DadosEmailCobrancaAtumoticaDTO dadosEmail) {
        Log.info("Montando dadosEmailCobranca: " + dadosEmail);
        if (dadosEmail == null) {
            Log.warn("Dados de cobrança simples não encontrados para o contrato: " + dadosEmail);
            return null;
        }
        String nomeArquivo = "boleto_contrato_" + dadosEmail.getNumeroContrato() + "_sistema_" + dadosEmail.getIdSistema();
        dadosEmail.setNomeArquivo(nomeArquivo);
        DadosEmailCobrancaMultipartDTO dadosEmailCobranca = new DadosEmailCobrancaMultipartDTO();
        BoletoDTO boletoDTO = criarBoletoDTO(dadosEmail);

        byte[] anexo;

        anexo = gerarBoletoCobrancaSimples(boletoDTO);
        Thread.sleep(3000);
        if (anexo == null) {
            return null;
        }
        dadosEmailCobranca.setDadosEmail(dadosEmail);
        dadosEmailCobranca.setAnexo(anexo);
        return dadosEmailCobranca;
    }

    private List<DadosEmailCobrancaAtumoticaDTO> buscarDadosEmailCobrancaAtumoticaSimples() {
        return cobrancaAutomaticaRepository.obterDadosEmailCobrancaAtumoticaSimples();
    }

    private BoletoDTO criarBoletoDTO(DadosEmailCobrancaAtumoticaDTO dadosEmail) {
        BoletoDTO boletoDTO = new BoletoDTO();
        boletoDTO.setContId(dadosEmail.getNumeroContrato());
        boletoDTO.setNumeroParcelas(dadosEmail.getParcela());
        boletoDTO.setSistemaId(dadosEmail.getIdSistema());
        boletoDTO.setOperadorId(OPERATOR_ID);
        return boletoDTO;
    }

    private byte[] gerarBoletoCobrancaSimples(BoletoDTO dto) {
        try {
            Log.info("consumindo o cr5 gerar boleto");
            return cr5WebservicesRestClient.gerarBoletoEImprimir(dto);
        } catch (Exception e) {
            Log.error("Erro ao chamar o serviço de geração de boleto.", e);
            return null;
        }
    }

    @Override
    public void enviarEmailCobrancaAutomaticaSimples() {
        Log.info("Buscando cobranças automáticas para envio de e-mail");
        List<DadosEmailCobrancaAtumoticaDTO> dadosEmailList = buscarDadosEmailCobrancaAtumoticaSimples();
        Log.info("Foram encontrados: " + dadosEmailList.size());
        if (dadosEmailList.isEmpty()) {
            Log.info("Não foi encontrado nenhuma cobrança automática");
        } else {
            Log.info("Foram encontradas " + dadosEmailList.size() + " cobranças automáticas.");
            for (DadosEmailCobrancaAtumoticaDTO dadosEmail : dadosEmailList) {
                DadosEmailCobrancaMultipartDTO dto = montarEmailCobranca(dadosEmail);
                if (dto != null && dto.getAnexo() != null) {
                    try {
                        Response response = emailServicesRestClient.enviarEmailCobrancaAutomatica(dto);
                        if (response.getStatus() == 200 || response.getStatus() == 204) {
                            Log.info("E-mail enviado com sucesso");
                            cobrancaAutomaticaRepository.salvarStatusEnvioCobrancaAutomatica(dto.getDadosEmail().getIdCobrancaAutomatica(), 1, null, new Date());
                        }
                    } catch (Exception e) {
                        Log.error("Erro ao enviar o e-mail de cobrança automática.", e);
                        cobrancaAutomaticaRepository.salvarStatusEnvioCobrancaAutomatica(dto.getDadosEmail().getIdCobrancaAutomatica(), 0, "Não foi possivél enviar por falta de dados!", null);
                    }
                } else {
                    Log.warn("Não foi possível montar o email de cobrança simples.");
                    assert dto != null;
                    cobrancaAutomaticaRepository.salvarStatusEnvioCobrancaAutomatica(dadosEmail.getIdCobrancaAutomatica(), 0, "Não foi possível montar o email de cobrança simples!", null);

                }
            }
        }
    }

    @Override
    public void enviarEmailCobrancaAutomaticaAgrupada() {
        Log.info("Buscando cobranças automáticas agrupadas para envio de e-mail");
        List<DadosEmailCobrancaAtumoticaDTO> dadosEmailList = buscarDadosEmailCobrancaAtumoticaAgrupadas();

        if (dadosEmailList.isEmpty()) {
            Log.info("Não foi encontrado nenhuma cobrança automática agrupadas");
        } else {
            Log.info("Foram encontradas " + dadosEmailList.size() + " cobranças automáticas agrupadas.");
            for (DadosEmailCobrancaAtumoticaDTO dadosEmail : dadosEmailList) {
                DadosEmailCobrancaMultipartDTO dto = montarEmailCobranca(dadosEmail);
                if (dto != null && dto.getAnexo() != null) {
                    try {
                        Response response = emailServicesRestClient.enviarEmailCobrancaAutomaticaAgrupada(dto);
                        if (response.getStatus() == 200 || response.getStatus() == 204) {
                            cobrancaAutomaticaRepository.salvarStatusEnvioCobrancaAutomatica(dto.getDadosEmail().getIdCobrancaAutomatica(), 1, null, new Date());
                        }
                    } catch (Exception e) {
                        Log.error("Erro ao enviar o e-mail de cobrança automática.", e);
                        cobrancaAutomaticaRepository.salvarStatusEnvioCobrancaAutomatica(dto.getDadosEmail().getIdCobrancaAutomatica(), 0, "Não foi possivél enviar por falta de dados!", null);
                    }
                } else {
                    Log.warn("Não foi possível montar o email de cobrança agrupada.");
                    assert dto != null;
                    cobrancaAutomaticaRepository.salvarStatusEnvioCobrancaAutomatica(dto.getDadosEmail().getIdCobrancaAutomatica(), 0, "Não foi possível montar o email de cobrança agrupada!", null);

                }
            }
        }
    }

    private List<DadosEmailCobrancaAtumoticaDTO> buscarDadosEmailCobrancaAtumoticaAgrupadas() {
        return cobrancaAutomaticaRepository.obterDadosEmailCobrancaAtumoticaAgrupadas();
    }

    @Override
    public void enviarEmailNotificacaoCobrancaAutomatica() {
        Log.info("Buscando cobranças automáticas para envio de e-mail");
        List<DadosEmailNotificacaoCobrancaAtumoticaDTO> dadosEmailNotificacaoList = cobrancaAutomaticaRepository.obterDadosEmailNotificacaoCobrancaAtumotica();

        if (dadosEmailNotificacaoList.isEmpty()) {
            Log.info("Não foi encontrado nenhuma cobrança automática");
        } else {
            Log.info("Foram encontradas " + dadosEmailNotificacaoList.size() + " cobranças automáticas.");
            for (DadosEmailNotificacaoCobrancaAtumoticaDTO dadosEmail : dadosEmailNotificacaoList) {
                try {
                    emailServicesRestClient.enviarEmailCobrancaAutomaticaNotificacao(dadosEmail);
                } catch (Exception e) {
                    Log.error("Erro ao enviar o e-mail de cobrança automática.", e);
                }
            }
        }
    }

    @Override
    public void enviarEmailNotificacaoCobrancaAutomaticaGestor() {
        Log.info("🔍 Buscando cobranças automáticas para envio de e-mail...");

        List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> dadosEmailNotificacaoList =
                cobrancaAutomaticaRepository.obterDadosNotificacaoGestor();

        if (dadosEmailNotificacaoList == null || dadosEmailNotificacaoList.isEmpty()) {
            Log.info("✅ Nenhuma cobrança automática encontrada.");
            return;
        }

        Log.info("📦 Foram encontradas " + dadosEmailNotificacaoList.size() + " cobranças automáticas.");

        // Agrupamento por CPF_CNPJ + produtoServico + parcela
        Map<ChaveGrupoCobranca, List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO>> grupos =
                dadosEmailNotificacaoList.stream()
                        .collect(Collectors.groupingBy(ChaveGrupoCobranca::of));

        for (Map.Entry<ChaveGrupoCobranca, List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO>> entry : grupos.entrySet()) {
            ChaveGrupoCobranca chave = entry.getKey();
            List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> grupo = entry.getValue();

            if (grupo == null || grupo.isEmpty()) {
                Log.warn("⚠️ Grupo vazio ignorado: " + chave);
                continue;
            }

            DadosEmailNotificacaoCobrancaAtumoticaGestorDTO base = grupo.get(0);
            Integer idUnidade = base.getIdUnidade();

            try {
                List<GestorDTO> gestores = cobrancaAutomaticaRepository.obterDadosDoGestor(idUnidade);

                if (gestores == null || gestores.isEmpty()) {
                    Log.warn("⚠️ Nenhum gestor encontrado para unidade " + idUnidade + " | Grupo: " + chave);
                    continue;
                }

                for (GestorDTO gestor : gestores) {
                    // Preencher os dados do gestor no DTO de cada cobrança do grupo
                    for (DadosEmailNotificacaoCobrancaAtumoticaGestorDTO dto : grupo) {
                        dto.setGestorResponsavel(gestor.getNome());
                        dto.setEmailGestorResponsavel(gestor.getEmail());
                    }

                    // Envia um único e-mail com todas as cobranças do grupo
                    emailServicesRestClient.enviarEmailCobrancaAutomaticaNotificacaoGestor(grupo);

                    Log.info("📧 E-mail enviado para " + gestor.getEmail() + " | " + chave + " | Cobranças: " + grupo.size());
                }

            } catch (Exception e) {
                Log.error("❌ Erro ao enviar o e-mail do grupo: " + chave, e);
            }
        }
    }

    public record ChaveGrupoCobranca(String cpfCnpj, String proposta, Integer parcela) {
        public static ChaveGrupoCobranca of(DadosEmailNotificacaoCobrancaAtumoticaGestorDTO dto) {
            String cpf = dto.getCpfCnpj() != null ? dto.getCpfCnpj().trim().replaceAll("[^0-9]", "") : "NULL";
            String proposta = dto.getProposta() != null ? dto.getProposta().trim().toUpperCase() : "NULL";
            Integer parcela = dto.getParcela() != null ? dto.getParcela() : -1;
            return new ChaveGrupoCobranca(cpf, proposta, parcela);
        }

        @Override
        public String toString() {
            return "CPF_CNPJ: " + cpfCnpj + " | PROPOSTA: " + proposta + " | PARCELA: " + parcela;
        }
    }



}
