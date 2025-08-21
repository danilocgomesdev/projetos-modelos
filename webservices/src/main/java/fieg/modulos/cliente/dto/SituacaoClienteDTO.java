package fieg.modulos.cliente.dto;

import fieg.core.pagination.PageQuery;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SituacaoClienteDTO extends PageQuery {

    private String statusInterface;
    private Integer idSistema;
    private String clienteDescricao;
    private String cpfCnpj ;
    private String codUnidade;
    private String unidadeDescricao;
    private char entidade;
    private String dtInicioVigencia;
    private String dtTerminoVigencia;
    private Integer contId;
    private Integer parcela;
    private BigDecimal vlCobranca;
    private BigDecimal vlPago;
    private BigDecimal vlEstorno;
    private String dtVencimento;
    private String dtPagamento;
    private String dtCredito;
    private String dtEstorno;
    private String dtCancelamento;
    private String cbcSituacao;
    private String objetoContrato;
    private Integer idCobrancaCliente;
    private String nossoNumero ;
    private String rede ;




}
