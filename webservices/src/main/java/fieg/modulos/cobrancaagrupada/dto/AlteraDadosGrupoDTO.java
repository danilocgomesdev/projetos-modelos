package fieg.modulos.cobrancaagrupada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class AlteraDadosGrupoDTO {

    private String notaFiscal;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataEmissaoNotaFiscal;
    private String avisoLancamentoNota;
    private LocalDateTime dataAvisoLancamentoNota;
    private Integer idOperador;
}
