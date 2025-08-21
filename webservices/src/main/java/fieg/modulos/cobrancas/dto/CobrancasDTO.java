package fieg.modulos.cobrancas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CobrancasDTO {

    private String entidade;
    private String codUnidade;
    private String unidade;
    private String filial;
    private String responsavelFinanceiro;
    private String cpfCnpj;
    private LocalDate nascimento;
    private String consumidor;
    private Integer idSistema;
    private String sistema;
    private Integer ano;
    private String produtoServico;
    private String protheus;
    private String proposta;
    private Integer contrato;
    private Integer idCobrancaAutomatica;
    private Integer idProduto;
    private Integer idOperadorConsultor;
    private String consultor;
}
