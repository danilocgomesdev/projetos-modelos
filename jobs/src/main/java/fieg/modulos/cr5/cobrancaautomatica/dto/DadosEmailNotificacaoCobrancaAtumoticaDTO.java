package fieg.modulos.cr5.cobrancaautomatica.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DadosEmailNotificacaoCobrancaAtumoticaDTO {

    private Integer idCobrancaAutomatica;
    private String entidade;
    private String produtoServico;
    private Integer numeroContrato;
    private BigDecimal valorCobranca;
    private String unidade;
    private Date vencimento;
    private Date dataInclusao;
    private String responsavelFinanceiro;
    private String cpfCnpj;
    private String proposta;
    private String notaFiscal;
    private BigDecimal totalBoleto;
    private String emailResponsavel;

}
