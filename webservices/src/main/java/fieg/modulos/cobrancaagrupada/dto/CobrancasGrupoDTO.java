package fieg.modulos.cobrancaagrupada.dto;

import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CobrancasGrupoDTO {

    private Integer contrato;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataPagamento;
    private Integer parcela;
    private SituacaoCobrancaCliente situacao;
    private String nossoNumero;
    private String cpfCnpj;
    private String sacadoNome;
    private BigDecimal valorCobranca;
    private BigDecimal totalDebito;
    private BigDecimal totalDesconto;
    private BigDecimal valorTotalParcela;
    private String notaFiscal;
    private LocalDateTime dataEmissaoNotaFiscal;
    private String avisoLancamentoNota;
    private LocalDateTime dataAvisoLancamentoNota;
    private String status;
    private Integer idSistema;
    private Integer idUnidadeContrato;
    private Integer idCobrancaAgrupada;
    private Integer idCobrancaCliente;
    private Integer idInterface;
    private LocalDateTime terminoVigenciaCobranca;
    private Integer idGrupoCancelado;


}
