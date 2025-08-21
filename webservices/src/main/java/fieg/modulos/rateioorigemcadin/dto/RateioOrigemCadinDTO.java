package fieg.modulos.rateioorigemcadin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RateioOrigemCadinDTO {
    private Integer id;
    private LocalDateTime dataInclusao;
    private Integer idCobrancaClienteCadin;
    private Integer idCobrancaClienteOrigem;
    private Integer codigoAmortizaBoletoPago;
    private String formaPagamento;
    private BigDecimal valorTotalCobranca;
    private BigDecimal porcentagemRateio;
    private BigDecimal desconto;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal custo;
    private BigDecimal valorPago;
    private LocalDateTime dataBaixaProtheus;
}
