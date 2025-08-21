package fieg.externos.caixaboletoservice.consulta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TituloDTO {
    private String numeroDocumento;
    private String dataVencimento;
    private String valor;
    private String tipoEspecie;
    private String flagAceite;
    private String dataEmissao;
    private JurosMoraDTO jurosMora;
    private String valorAbatimento;
    private PosVencimentoDTO posVencimento;
    private String codigoMoeda;
    private PagadorDTO pagador;
    private MultaDTO multa;
    private List<DescontoDTO> descontos;
    private String valorIof;
    private String identificacaoEmpresa;
    private PagamentoDTO pagamento;
    private String codigoBarras;
    private String linhaDigitavel;
    private String url;
}
