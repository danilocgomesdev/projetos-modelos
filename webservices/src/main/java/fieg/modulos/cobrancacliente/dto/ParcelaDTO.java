package fieg.modulos.cobrancacliente.dto;

import fieg.modulos.cobrancacliente.enums.FormaPagamento;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import fieg.modulos.interfacecobranca.enums.StatusInterface;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// TODO enriquecer conforme necessário
@Data
public class ParcelaDTO {

    // Informações do contrato
    private Integer idInterface;
    private Integer contId;
    private Integer idSistema;
    private StatusInterface statusContrato;
    private IntegraProtheus integraProtheus;
    private String sacadoNome;
    private String sacadoCpfCnpj;
    private String contratoProtheus;
    private Integer quantidadeDeParcelas;

    // Informações realmente da parcela
    private Integer idCobrancaCliente;
    private Integer idCancelamento;
    private LocalDateTime dataGeracao;
    private BigDecimal valorCobranca;
    private BigDecimal valorAgente;
    private BigDecimal valorBolsa;
    private BigDecimal valorDescontoComercial;
    private BigDecimal valorPago;
    private LocalDateTime dataBaixa;
    private Integer numeroParcela;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataPagamento;
    private LocalDateTime dataCredito;
    private FormaPagamento formaPagamento;
    private BigDecimal multa;
    private BigDecimal juros;
    private BigDecimal imposto;
    private Boolean retirarJuros;
    private String motivoRetiradaJuros;
    private SituacaoCobrancaCliente situacao;
    private Boolean estornar;
    private LocalDateTime dataEstorno;
    private BigDecimal valorEstorno;
    private Integer idCobrancaPagamento;
    private Integer idUnidade;
    private String codigoUnidade;
    private LocalDateTime dataEmissaoNotaFiscal;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorInclusao;
    private Integer idOperadorAlteracao;
    private Float porcentagemEstornar;
    private String origemValorEstornado;
    private LocalDateTime dataAlteracaoProtheus;
    private LocalDateTime dataEstornoProtheus;
    private LocalDateTime dataInclusaoProtheus;
    private BigDecimal descontoAteVencimento;
    private Integer recno;
    private Entidade entidade;

    // não vem diretamente de nenhuma entidade
    private FormaPagamentoSimplificado formaPagamentoSimplificado; // nem sempre será preenchido
}
