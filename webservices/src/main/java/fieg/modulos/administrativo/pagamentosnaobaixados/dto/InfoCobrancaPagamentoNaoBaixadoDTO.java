package fieg.modulos.administrativo.pagamentosnaobaixados.dto;

import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import fieg.modulos.interfacecobranca.enums.StatusInterface;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InfoCobrancaPagamentoNaoBaixadoDTO {
    private Integer idCobrancaCliente;
    private Integer contId;
    private Integer idSistema;
    private Integer numeroParcela;
    private BigDecimal valorCobranca;
    private SituacaoCobrancaCliente situacaoParcela;
    private StatusInterface statusContrato;
}
