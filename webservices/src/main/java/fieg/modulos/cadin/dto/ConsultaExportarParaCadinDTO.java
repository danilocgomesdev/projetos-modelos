package fieg.modulos.cadin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConsultaExportarParaCadinDTO {

    private String nome;
    private String cpfCnpj;
    private BigDecimal valorCobranca;
    private LocalDate dataVencimento;
    private String unidade;
    private String situacao;
    private String status;
    private Integer contrato;
    private Integer parcela;
    private Integer sistema;
    private LocalDate dataNotaFiscal;
    private String objeto;
    private String numeroNotaFiscal;
    private BigDecimal descontos;
    private String nossoNumero;
    private Integer idCobrancasClientes;
    private String statusListar;

}
