package fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5;

import fieg.modulos.boleto.enums.SituacaoBoleto;
import fieg.modulos.cobrancaagrupada.enums.SituacaoCobrancaAgrupada;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DadoBoletoCr5DTO {
    private Integer idBoleto;
    private String nossoNumero;
    private SituacaoBoleto bolSituacao;
    private BigDecimal bolValor;
    private BigDecimal bolDesconto;
    private BigDecimal bolJuros;
    private BigDecimal bolMulta;
    private BigDecimal bolPago;
    private LocalDateTime bolVencimento;
    private LocalDateTime bolDataPagamento;
    private LocalDateTime bolDataCredito;
    private LocalDateTime bolEmissao;
    private LocalDateTime bolCancelamento;
    private Integer idCobrancasCliente;
    private Integer idCobrancasAgrupada;
    private String operadorInclusao;
    private String operadorAlteracao;
    private String operadorCancelamento;
    private SituacaoCobrancaAgrupada grupoStatus;
    private Integer idUnidade;
    private Integer codUnidade;
    private String cnpjCedente;
    private String codigoBeneficiario;
    private Integer idArquivosRetornos;
    private Integer idArquivosDetalhes;
    private String bolRemessa;
}
