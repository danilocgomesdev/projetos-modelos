package fieg.modulos.conciliacao.conciliaexternos.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ConciliacaoHitsDTO {

    private Integer sistema;
    private String entidade;
    private Boolean criadoCr5;
    private String motivoFalha;
    private LocalDateTime dataPagamento;
    private Integer numeroParcela;
    private BigDecimal valorPago;
    private String contratoProtheus;
    private Integer contrato;
    private String sacadoNome;
    private String sacadoCpfCnpj;
    private LocalDateTime dataInclusaoProtheus;
    private LocalDateTime dataAlteracaoProtheus;
}
