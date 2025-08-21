package fieg.modulos.conciliacao.conciliaexternos.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConciliacaoDTO {

    private Integer idConciliacao;
    private Integer idSistema;
    private Integer idUnidade;
    private Integer idEntidade;
    private Integer contId;
    private Integer numeroParcela;
    private Boolean conciliado;
    private String motivoFalha;
    private Integer idOperadorAlteracao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;
    private LocalDateTime dataPagamento;
}
