package fieg.modulos.cobrancaagrupada.dto;


import fieg.modulos.unidade.model.UnidadeCR5;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class CobrancaParaContratoEmRedeDTO {

    private Integer idCobrancaCliente;
    private Integer idCobrancaAgrupada;
    private Integer idInterface;
    private String cor;
    private String filialERP;
    private String descricaoUnidade;
    private String contratoProtheus;
    private Integer contratoProducao;
    private LocalDateTime dataVencimento;
    private Integer parcela;
    private String situacao;
    private String cpfCnpjResponsavelFinanceiro;
    private String nomeResponsavelFinanceiro;
    private String nomeConsumidor;
    private BigDecimal valorCobranca;
    private BigDecimal jurosEMultas;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal descontos;
    private BigDecimal valorPromocao;
    private BigDecimal valorBolsa;
    private BigDecimal valorDescontoComercial;
    private BigDecimal cbcValorAgente;
    private BigDecimal valorTotalParcela;
    private BigDecimal valorPago;

    private UnidadeCR5 unidade;

}
