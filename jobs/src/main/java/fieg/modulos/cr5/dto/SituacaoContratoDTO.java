package fieg.modulos.cr5.dto;

import fieg.modulos.cadin.dto.DadosAcordoCancelamentoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SituacaoContratoDTO {

    public static final int ID_OPERADOR_CANCELAMENTO_SISTEMA = 9039;

    public static final String MOTIVO_CANCELAMENTO_PADRAO = "CANCELAMENTO AUTOMATICO - APÓS 7 DIAS DO VENCIMENTO DA PRIMEIRA PARCELA";

    private static final String STATUS_INTERFACE_CANCELADO = "CANCELADO";
    private static final String STATUS_INTERFACE_EXCLUIDO = "EXCLUIDO";

    private Integer contId;
    private Integer sistemaId;
    private Integer operadorId;
    private String motivoCancelamento;
    private Date dataCancelamento;
    private Boolean isEdicaoSneAutorizada;
    private String statusInterface;
    private boolean cancelamentoAutomatico;

    public static SituacaoContratoDTO criaDTOparaCancelamentoPadrao(Integer contId, Integer sistemaId) {
        return new SituacaoContratoDTO(
                contId,
                sistemaId,
                ID_OPERADOR_CANCELAMENTO_SISTEMA,
                MOTIVO_CANCELAMENTO_PADRAO,
                new Date(),
                true,
                null,
                true
        );
    }

    public static SituacaoContratoDTO criaDTOparaCancelamentoCadin(DadosAcordoCancelamentoDTO dadosAcordoCancelamentoDTO) {
        return new SituacaoContratoDTO(
                dadosAcordoCancelamentoDTO.getCodigoAcordoEfetuado(),
                25,
                dadosAcordoCancelamentoDTO.getIdOperador(),
                dadosAcordoCancelamentoDTO.getMotivoCancelamento(),
                new Date(),
                true,
                null,
                true
        );
    }
}
