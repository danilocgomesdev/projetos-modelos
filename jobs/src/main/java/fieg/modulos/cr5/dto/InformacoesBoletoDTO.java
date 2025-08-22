package fieg.modulos.cr5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InformacoesBoletoDTO implements Serializable {

    // Informações no banco do CR5
    private String cedente;
    private String codigoBeneficiario;
    private String nossoNumero;
    private String cnpjCedente;
    private Integer idBoleto;
    private BigDecimal valorBoleto;
    private String statusCR5;
    private Date dataGeracao;
    private Integer idCobrancaCliente;
    private String unidade;

    // Informações que vem da caixa
    private Boolean pagoNaOrigem;
    private String statusOrigem;
}
