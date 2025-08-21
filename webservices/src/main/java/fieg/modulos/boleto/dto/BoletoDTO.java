package fieg.modulos.boleto.dto;

import fieg.modulos.boleto.enums.SituacaoBoleto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BoletoDTO {

    private Integer idBoleto;
    private LocalDateTime dataDocumento;
    private LocalDateTime dataVencimento;
    private BigDecimal valorDocumento;
    private String nossoNumero;
    private Integer anoReferencia;
    private Integer mesReferencia;
    private String observacao1;
    private String observacao2;
    private String observacao3;
    private String observacao4;
    private String observacao5;
    private String localPagamento;
    private Integer contador = 0;
    private SituacaoBoleto situacaoPagamento;
    private Integer idUnidade;
    private LocalDateTime dataCancelamento;
    private Integer idOperadorCancelamento;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;
    private String identificacaoArquivoBaixa;
    private LocalDateTime dataVencimentoInicial;
}
