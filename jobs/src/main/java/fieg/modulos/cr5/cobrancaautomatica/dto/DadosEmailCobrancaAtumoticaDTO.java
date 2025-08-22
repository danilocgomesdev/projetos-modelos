package fieg.modulos.cr5.cobrancaautomatica.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DadosEmailCobrancaAtumoticaDTO {

    private Integer idCobrancaAutomatica;
    private String produtoServico;
    private String entidade;
    private Integer numeroContrato;
    private Integer idSistema;
    private Integer parcela;
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
    private String nomeArquivo;

}
