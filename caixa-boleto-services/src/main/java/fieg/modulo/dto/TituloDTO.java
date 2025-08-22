package fieg.modulo.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TituloDTO {

    private String nossoNumero;
    private String numeroDocumento;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private String tipoEspecie;
    private String flagAceite;
    private LocalDate dataEmissao;
    private String valorAbatimento;
    private String codigoMoeda;
    private JurosMoraDTO jurosMora;
    private PosVencimentoDTO posVencimento;
    private PagadorDTO pagador;
    private FichaCompensacaoDTO fichaCompensacao;
    private ReciboPagadorDTO reciboPagador;
    private PagamentoDTO pagamento;
    private MultaDTO multa;
    private List<DescontoDTO> descontos;

}
