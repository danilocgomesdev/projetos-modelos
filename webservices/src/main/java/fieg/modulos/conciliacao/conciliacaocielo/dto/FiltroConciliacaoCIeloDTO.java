package fieg.modulos.conciliacao.conciliacaocielo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FiltroConciliacaoCIeloDTO {
    private LocalDate dataTransacaoInicial;
    private LocalDate dataTransacaoFinal;
    private LocalDate dataVencimentoInicial;
    private LocalDate dataVencimentoFinal;
    private String nsu;
    private String autorizacao;
    private String tid;
    private Integer parcela;
}
