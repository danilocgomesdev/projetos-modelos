package fieg.modulos.boleto.service;


import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.externos.caixaboletoservice.consulta.dto.ConsultaBoletoCaixaResponseDTO;
import fieg.externos.caixaboletoservice.consulta.service.CaixaBoletoService;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoNossoNumeroResponseDTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.CobrancaClienteGrupoDTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadoBoletoCr5DTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadosBoletoCr5DTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadosTituloBoletoCr5DTO;
import fieg.modulos.boleto.enums.SituacaoBoleto;
import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.boleto.repository.BoletoRepository;
import fieg.modulos.boletocancelado.model.BoletoCancelado;
import fieg.modulos.boletocancelado.service.BoletoCanceladoService;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancaagrupada.service.CobrancaAgrupadaService;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
class BoletoServiceImpl implements BoletoService {

    @Inject
    BoletoRepository boletoRepository;

    @Inject
    CaixaBoletoService caixaBoletoService;

    @Inject
    Mapper<ConsultaBoletoNossoNumeroResponseDTO, BoletoFilterDTO> consultaBoletoToBoletoFilterMapper;

    @Inject
    CobrancaAgrupadaService cobrancaAgrupadaService;

    @Inject
    BoletoCanceladoService boletoCanceladoService;

    @Inject
    CobrancaClienteService cobrancaClienteService;


    @Override
    public Optional<Boleto> getById(Integer idBoleto) {
        return boletoRepository.getBoletoById(idBoleto);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cancelarBoleto(CobrancaCliente cobrancaCliente, Integer idOperador) {
        Boleto boleto = boletoRepository.getBoletoById(cobrancaCliente.getBoleto().getId()).orElseThrow(() -> new NegocioException("Boleto não encontrato"));
        verificaSeEstaPagoNaCaixa(boleto);
        realizarCancelamento(cobrancaCliente, boleto, idOperador);
    }

    private void realizarCancelamento(CobrancaCliente cobrancaCliente, Boleto boleto, Integer idOperador) {

        boleto.setDataAlteracao(LocalDateTime.now());
        boleto.setIdOperadorAlteracao(idOperador);
        boleto.setIdOperadorCancelamento(idOperador);
        boleto.setDataCancelamento(LocalDateTime.now());
        boleto.setSituacaoPagamento(SituacaoBoleto.CANCELADO);
        boleto.setStatusRemessa("ROC");

        cobrancaCliente.setBoleto(null);

        BoletoCancelado boletoCancelado = new BoletoCancelado();
        boletoCancelado.setIdBoleto(boleto.getId());
        boletoCancelado.setIdCobrancaCliente(cobrancaCliente.getId());

        boletoRepository.atualizaBoleto(boleto);
        cobrancaClienteService.atualizaCobrancaCliente(cobrancaCliente);
        boletoCanceladoService.salvar(boletoCancelado);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cancelarBoletoGrupo(CobrancaAgrupada cobrancaAgrupada, Integer idOperador) {
        Boleto boleto = boletoRepository.getBoletoById(cobrancaAgrupada.getBoleto().getId()).orElseThrow(() -> new NegocioException("Boleto não encontrato"));
        verificaSeEstaPagoNaCaixa(boleto);
        realizarCancelamentoGrupo(cobrancaAgrupada, boleto, idOperador);
    }

    @Override
    public DadosBoletoCr5DTO consultaDadosBoleto(BoletoFilterDTO consultaBoletoFilterDTO) {
        List<DadosTituloBoletoCr5DTO> dadosTitulo = null;

        // 1. Consultar dados do BOLETO
        DadoBoletoCr5DTO dadoBoleto = boletoRepository.buscarDadoBoleto(consultaBoletoFilterDTO.getNossoNumero());

        if (dadoBoleto == null) {
            throw new NegocioException("Nenhum boleto encontrado com o Nosso Número especificado.");
        }

        // 2. Verifica se o GRUPO foi CANCELADO
        if (dadoBoleto.getIdCobrancasAgrupada() != null) {
            CobrancaClienteGrupoDTO clienteGrupoDTO = boletoRepository.buscarCobrancasPorNossoNumero(consultaBoletoFilterDTO.getNossoNumero());
            dadosTitulo = boletoRepository.buscarTitulosBoleto(consultaBoletoFilterDTO.getNossoNumero(), clienteGrupoDTO);
        } else {
            CobrancaClienteGrupoDTO clienteGrupoDTO = new CobrancaClienteGrupoDTO(Collections.singletonList(dadoBoleto.getIdCobrancasCliente()), null);
            // 2. Busca os dados dos títulos vinculados ao boleto
            dadosTitulo = boletoRepository.buscarTitulosBoleto(consultaBoletoFilterDTO.getNossoNumero(), clienteGrupoDTO);
        }

        // 3. Monta o DTO final
        DadosBoletoCr5DTO resultado = new DadosBoletoCr5DTO();
        resultado.setDadoBoletoCR5(dadoBoleto);
        resultado.setDadosTituloBoletoCR5(dadosTitulo);

        return resultado;
    }

    @Override
    public void retiraVinculoBoleto(Integer idBoleto, Integer idOperador) {
        try {

            CobrancaCliente cobrancaCliente = cobrancaClienteService.buscaCobrancaClientePorIdBoleto(idBoleto);
            if (cobrancaCliente != null) {
                cobrancaClienteService.retiraVinculoBoleto(cobrancaCliente.getId(), idOperador);
                return;
            }

            CobrancaAgrupada cobrancaAgrupada = cobrancaAgrupadaService.buscaGrupoPorIdBoleto(idBoleto);
            if (cobrancaAgrupada != null) {
                cobrancaAgrupadaService.retiraVinculoBoleto(cobrancaAgrupada.getId(), idOperador);
                return;
            }
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível remover o vínculo!");
        }
    }

    private void realizarCancelamentoGrupo(CobrancaAgrupada cobrancaAgrupada, Boleto boleto, Integer idOperador) {

        boleto.setDataAlteracao(LocalDateTime.now());
        boleto.setIdOperadorAlteracao(idOperador);
        boleto.setIdOperadorCancelamento(idOperador);
        boleto.setDataCancelamento(LocalDateTime.now());
        boleto.setSituacaoPagamento(SituacaoBoleto.CANCELADO);
        boleto.setStatusRemessa("ROC");

        cobrancaAgrupada.setBoleto(null);

        BoletoCancelado boletoCancelado = new BoletoCancelado();
        boletoCancelado.setIdBoleto(boleto.getId());
        boletoCancelado.setIdCobrancaAgrupada(cobrancaAgrupada.getId());

        boletoRepository.atualizaBoleto(boleto);
        cobrancaAgrupadaService.atualizaCobrancaAgrupada(cobrancaAgrupada);
        boletoCanceladoService.salvar(boletoCancelado);
    }

    private void verificaSeEstaPagoNaCaixa(Boleto boleto) {
        Optional<ConsultaBoletoNossoNumeroResponseDTO> consultaBoletoResponseDTO = boletoRepository.pesquisaUsandoNossoNumero(boleto.getNossoNumero());

        if (consultaBoletoResponseDTO.isPresent()) {
            BoletoFilterDTO boletoFilterDTO = consultaBoletoToBoletoFilterMapper.map(consultaBoletoResponseDTO.get());

            ConsultaBoletoCaixaResponseDTO boletoResponseDTO = caixaBoletoService.consultaBoletoCaixa(boletoFilterDTO);

            if (boletoResponseDTO.getBody().getServicoSaida().getDados().getControleNegocial().getMensagens().get(0).getRetorno().contains("LIQUIDADO") ||
                    boletoResponseDTO.getBody().getServicoSaida().getDados().getControleNegocial().getMensagens().get(0).getRetorno().contains("TITULO JA PAGO NO DIA")) {
                throw new NegocioException("O boleto encontra pago, aguarde a baixa!");
            }

            caixaBoletoService.baixaBoletoCaixa(boletoFilterDTO);
        }
    }


}
