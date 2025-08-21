package fieg.modulos.cobrancacliente.dto;

import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class CobrancaClienteAdicionarDTO {

        private Integer numParcela;
        private Integer numParcelaProtheus;
        private SituacaoCobrancaCliente situacao;
        private BigDecimal valorCobranca;
        private LocalDateTime dataVencimento;
        private Integer idCobrancaCliente;
        private Integer codigoUnidade;
        private Integer idInterface;
        private String nomeEntidade;
        private String sacadoNome;
        private String sacadoCpfCnpj;
        private Integer idSistema;
        private Integer idBoleto;
        private String contratoProtheus;
}
