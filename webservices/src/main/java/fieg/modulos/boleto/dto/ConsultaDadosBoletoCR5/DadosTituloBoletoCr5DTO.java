package fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5;

import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import fieg.modulos.interfacecobranca.enums.StatusInterface;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DadosTituloBoletoCr5DTO {
    private String entidade;
    private String vinculo; // "REMOVIDO" ou "ATIVO"
    private Integer unidade;
    private String responsavelFinanceiro;
    private String cpfCnpj;
    private String protheus;
    private StatusInterface status;
    private Integer contrato;
    private Integer parcela;
    private String situacao;
    private BigDecimal cobranca;
    private BigDecimal descVenc;
    private BigDecimal abatimento;
    private BigDecimal fiegOvg;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal pago;
    private LocalDateTime vencimento;
    private LocalDateTime pagamento;
    private LocalDateTime credito;
    private Integer idCbc;
    private Integer recno;
    private Integer grupo;
    private Integer grupoCancelado;
    private String boletoAtivo;
}
