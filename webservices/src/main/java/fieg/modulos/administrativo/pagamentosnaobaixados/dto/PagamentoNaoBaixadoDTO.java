package fieg.modulos.administrativo.pagamentosnaobaixados.dto;

import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PagamentoNaoBaixadoDTO {
    private FormaPagamentoSimplificado formaPagamento;
    private LocalDateTime dataPagamento;
    private String sacadoCpfCnpj;
    private String sacadoNome;
    private Integer idUnidade;
    private BigDecimal valorOperacao;
    private Integer parcelamento;
    private Integer idOperadorRecebimento;
    private Integer idPedido;

    private List<InfoCobrancaPagamentoNaoBaixadoDTO> cobrancas;
}
