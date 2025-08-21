package fieg.modulos.cobrancaagrupada.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class DadosCobrancasGrupoDTO {
    private Integer idUnidade;
    private Integer contrato;
    private Integer idSistema;
    private String protheusContrato;
    private Integer parcela;
    private String consumidorNome;
    private BigDecimal valorCobranca;
    private String nossoNumero;
    private String situacao;
}
