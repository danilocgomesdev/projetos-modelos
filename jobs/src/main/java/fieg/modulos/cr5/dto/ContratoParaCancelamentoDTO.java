package fieg.modulos.cr5.dto;

import fieg.core.util.StringUtils;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContratoParaCancelamentoDTO implements Serializable {

    private Integer idInterfaceCobranca;
    private Integer contId;
    private Integer idSistema;
    private String statusInterface;
    private String contratoProtheus;
    private Integer idProtheusContrato;
    private String filialERP;
    private String entidade;
    private Integer idBoleto;
    private Integer idUnidadeContrato;

    public boolean naoEstaIntegrado() {
        if (!ehProtheus()) {
            throw new IllegalArgumentException("Não faz sentido validar se um contrato não Protheus está integrado");
        }
        return StringUtils.isBlank(contratoProtheus);
    }

    public boolean ehProtheus() {
        return idProtheusContrato != null;
    }
}
