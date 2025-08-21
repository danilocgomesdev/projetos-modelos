package fieg.externos.protheus.contasareceber.dto;

import fieg.externos.protheus.movimentacaobancaria.dto.MovimentacaoBancariaProtheusDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// SE1010
@Data
public class ContasAReceberProtheusDTO {

    private Integer recno;
    private String filial;
    private String cliente;
    private String historico;
    private String contrato;
    private String titulo;
    private String parcela;
    private String sistema;
    private String idInterface;
    private LocalDate dataEmissao;
    private BigDecimal cobranca;
    private BigDecimal valorLiquidado;
    private BigDecimal saldo;
    private LocalDate dataBaixa;
    private String revisaoDeContrato;
    private boolean excluido;
    private String tipo;

    private List<MovimentacaoBancariaProtheusDTO> baixas;
}
