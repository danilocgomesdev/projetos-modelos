package fieg.modulos.cobrancaagrupada.dto;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AlterarDataVencimentoDTO {
    private String filialErp;
    private String codUnidade;
    private String status;
    private Integer recno;
    private Integer contId;
    private Integer parcela;
    private String cpfCnpj;
    private String situacao;
    private Integer idSistema;
    private String vencimento;
    private String vencimentoGrupo;
    private Integer grupo;
    private String terminoCobranca;
    private String dtInclusaoProtheus;
    private Integer idProtheusContrato;
    private Integer tituloSne;
}
