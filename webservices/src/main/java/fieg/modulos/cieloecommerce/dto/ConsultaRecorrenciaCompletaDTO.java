package fieg.modulos.cieloecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConsultaRecorrenciaCompletaDTO {

    private Integer idEntidade;
    private String entidade;
    private String unidade;
    private String responsavelFinanceiro;
    private String cpfCnpj;

    private String idRecorrencia;
    private String statusRecorrencia;
    private String dataInicioRecorrencia;
    private String dataFimRecorrencia;

    private Integer contrato ;
    private Integer parcela;
    private String situacao;
    private Integer idSistema;
    private BigDecimal valorCobranca;
    private BigDecimal valorPago;
    private String dataVencimento;
    private String dataPagamento;
    private Integer idCobrancasclientes;

    private String dataVendaCielo;
    private String tidCielo;
    private String autorizacaoCielo;
    private String nsuCielo;
    private BigDecimal valorVendaCielo;



}