package fieg.modulos.cobrancaautomatica.dto;


import fieg.core.util.Mascaras;
import fieg.modulos.cobrancaautomatica.enums.EntidadeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DadosEmailNotificacaoCobrancaAtumoticaDTO {

    private Integer idCobrancaAutomatica;
    private EntidadeEnum entidade;
    private String produtoServico;
    private String numeroContrato;
    private BigDecimal valorCobranca;
    private String unidade;
    private String vencimento;
    private String dataInclusao;
    private String responsavelFinanceiro;
    private String cpfCnpj;
    private String proposta;
    private String notaFiscal;
    private BigDecimal totalBoleto;
    private String emailResponsavel;

    public String getCpfCnpj() {
        return Mascaras.formataCpfCnpj(cpfCnpj);
    }

    public String getTotalBoleto() {
        if (totalBoleto != null) {
            return Mascaras.formatarMoeda(totalBoleto);
        }
        return null;
    }
    public String getValorCobranca() {
        if (valorCobranca != null) {
            return Mascaras.formatarMoeda(valorCobranca);
        }
        return null;
    }

    public String getVencimento() {
        if (vencimento != null) {
            return Mascaras.formataDataBrasil(vencimento);
        }
        return null;
    }

    public String getDataInclusao() {
        if (dataInclusao != null) {
            return Mascaras.formataDataBrasil(dataInclusao);
        }
        return null;
    }

}
