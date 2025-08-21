package fieg.externos.cadin.parcelacadin.service;

import fieg.core.enums.Sistemas;
import fieg.core.exceptions.NegocioException;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoDTO;
import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoRateado;
import fieg.externos.cadin.amortizaboletopago.service.RateioCadinService;
import fieg.externos.cadin.parcelacadin.dto.BuscaParcelaCadinDTO;
import fieg.externos.cadin.parcelacadin.dto.InformacoesOrigemDTO;
import fieg.externos.cadin.parcelacadin.dto.ParcelaCadinDTO;
import fieg.externos.cadin.parcelacadin.repository.ParcelaCadinRepository;
import fieg.externos.protheus.contasareceber.service.ContasAReceberProtheusService;
import fieg.modulos.cobrancacliente.dto.FiltroCobrancasDTO;
import fieg.modulos.cobrancacliente.dto.ParcelaComInfoProtheusDTO;
import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroDataPagamento;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
class ParcelaCadinServiceImpl implements ParcelaCadinService {

    @Inject
    ParcelaCadinRepository parcelaCadinRepository;

    @Inject
    RateioCadinService rateioCadinService;

    @Inject
    CobrancaClienteService cobrancaClienteService;

    @Inject
    ContasAReceberProtheusService contasAReceberProtheusService;

    @Inject
    Mapper<CobrancaCliente, ParcelaDTO> mapper;

    @Override
    public ParcelaCadinDTO getParcelaCadinDTO(CobrancaCliente cobrancaCliente) {
        if (cobrancaCliente.getIdSistema() != Sistemas.CADIN.idSistema) {
            throw new NegocioException("Somente cobranças do cadin tem parcelas de origem");
        }

        List<Integer> parcelasDeOrigem = parcelaCadinRepository.getCobrancasDeOrigem(cobrancaCliente);
        List<CobrancaCliente> cobrancasDeOrigem = cobrancaClienteService.getAllById(parcelasDeOrigem);
        ParcelaDTO parcelaCadin = mapper.map(cobrancaCliente);
        parcelaCadin.setFormaPagamentoSimplificado(cobrancaClienteService.getFormaPagamentoSimplificada(parcelaCadin.getIdCobrancaCliente()));
        List<AmortizaBoletoDTO> amortizas = rateioCadinService.getAcordosRateados(cobrancaCliente);
        List<AmortizaBoletoRateado> acordosRateado = rateioCadinService.getRateiosExistentes(amortizas);
        Map<Integer, List<AmortizaBoletoRateado>> rateiosPorCobrancaOrigem = acordosRateado
                .stream()
                .collect(Collectors.groupingBy(it -> it.getAmortizaBoletoPago().getIdCobrancaClienteOrigem()));

        var parcelaComProtheusCadin = new ParcelaComInfoProtheusDTO();
        parcelaComProtheusCadin.setParcelaDTO(parcelaCadin);
        parcelaComProtheusCadin.setContasAReceberProtheus(contasAReceberProtheusService.getByCobrancaCliente(cobrancaCliente).orElse(null));

        var parcelaCadinDTO = new ParcelaCadinDTO();
        parcelaCadinDTO.setParcelaCadin(parcelaComProtheusCadin);

        List<InformacoesOrigemDTO> parcelasOrigemComProtheus = cobrancasDeOrigem.stream()
                .map(it -> {
                    var parcela = new ParcelaComInfoProtheusDTO();
                    parcela.setParcelaDTO(mapper.map(it));
                    parcela.setContasAReceberProtheus(contasAReceberProtheusService.getByCobrancaCliente(it).orElse(null));

                    var informacoesOrigemDTO = new InformacoesOrigemDTO();
                    informacoesOrigemDTO.setParcelaDeOrigem(parcela);
                    List<AmortizaBoletoRateado> amortizasDaParcela = rateiosPorCobrancaOrigem.get(it.getId());
                    if (amortizasDaParcela != null) {
                        informacoesOrigemDTO.setAcordoRateado(amortizasDaParcela.get(0));
                    }

                    return informacoesOrigemDTO;
                }).toList();

        parcelaCadinDTO.setInformacoesOrigem(parcelasOrigemComProtheus);

        return parcelaCadinDTO;
    }

    @Override
    public PageResult<ParcelaDTO> getParcelasCadinSomenteParcelas(PageQuery pageQuery, BuscaParcelaCadinDTO buscaParcelaCadinDTO) {
        return getCobrancasClienteCadin(pageQuery, buscaParcelaCadinDTO).map(mapper::map);
    }

    private PageResult<CobrancaCliente> getCobrancasClienteCadin(PageQuery pageQuery, BuscaParcelaCadinDTO buscaParcelaCadinDTO) {
        if (!buscaParcelaCadinDTO.podePesquisarSemData()) {
            if (buscaParcelaCadinDTO.getDataInicial() == null || buscaParcelaCadinDTO.getDataFinal() == null) {
                throw new ValorInvalidoException("É obrigatório informar data inicial e final!");
            }

            if (ChronoUnit.MONTHS.between(buscaParcelaCadinDTO.getDataInicial(), buscaParcelaCadinDTO.getDataFinal()) > 0) {
                throw new ValorInvalidoException("Não é permitido um intervalo maior que 1 mês!");
            }
        }

        if (buscaParcelaCadinDTO.getDataVencimentoInicial() != null || buscaParcelaCadinDTO.getDataVencimentoFinal() != null) {
            if (buscaParcelaCadinDTO.getDataVencimentoInicial() == null || buscaParcelaCadinDTO.getDataVencimentoFinal() == null) {
                throw new ValorInvalidoException("Informar a data inicial e final de vencimento!");
            }
        }

        return cobrancaClienteService.pesquisaUsandoFiltro(
                pageQuery,
                FiltroCobrancasDTO.builder()
                        .filtroPagamento(new FiltroDataPagamento(
                                buscaParcelaCadinDTO.getDataInicial(),
                                buscaParcelaCadinDTO.getDataFinal()
                        ))
                        .sacadoNome(buscaParcelaCadinDTO.getSacadoNome())
                        .sacadoCpfCnpj(buscaParcelaCadinDTO.getSacadoCpfCnpj())
                        .numeroParcela(buscaParcelaCadinDTO.getNumeroParcela())
                        .contId(buscaParcelaCadinDTO.getContId())
                        .idSistema(Sistemas.CADIN.idSistema)
                        .nossoNumero(buscaParcelaCadinDTO.getNossoNumero())
                        .baixaIntegrada(buscaParcelaCadinDTO.getBaixaIntegrada())
                        .idUnidade(buscaParcelaCadinDTO.getIdUnidade())
                        .idEntidade(buscaParcelaCadinDTO.getIdEntidade())
                        .formaPagamento(buscaParcelaCadinDTO.getFormaPagamento())
                        .filtroIntegraProtheus(buscaParcelaCadinDTO.getFiltroIntegraProtheus())
                        .dataVencimentoInicial(buscaParcelaCadinDTO.getDataVencimentoInicial())
                        .dataVencimentoFinal(buscaParcelaCadinDTO.getDataVencimentoFinal())
                        .build()
        );
    }
}
