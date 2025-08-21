package fieg.externos.cadin.amortizaboletopago.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AmortizaBoletoDTO {
    // É o id
    private Integer codigo;
    private BigDecimal valorJuros;
    private BigDecimal valorMulta;
    private BigDecimal valorCusta;
    private BigDecimal valorPago;
    private BigDecimal valorPrincial;
    private BigDecimal valorDesconto;
    private Integer idCobrancaClienteOrigem;
    private Character baixaParcial;
    private LocalDateTime dataBaixaProtheus;
    private LocalDateTime dataBaixaCr5;

    // Essas informações não existem na tabela, mas é útil ter no DTO
    private Integer recno;
    private Integer idCobrancaClienteCadin;
    private Integer codigoEntidade;
    private Integer numeroParcela;
    private Integer objetoDeInadiplencia;
    private String statusAcordo;
    private String statusObjetoCadin;
}
