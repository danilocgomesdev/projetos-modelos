package fieg.modulos.cr5.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ErroTimeoutProtheusDTO {

    private Integer id;
    private Integer idCobrancasClientes;
    private Date dataEnvio;
    private Boolean baixouProtheus;
    private Integer recno;
    private Date dataPagamento;
    private BigDecimal valorPago;
    private Integer idAmortizaBoletoPago;
    private Integer idRateioOrigemCadin;
    private String formaPagamento;
}
