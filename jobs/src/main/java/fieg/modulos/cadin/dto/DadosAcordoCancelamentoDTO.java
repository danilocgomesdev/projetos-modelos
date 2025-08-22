package fieg.modulos.cadin.dto;

import lombok.Data;

import java.util.List;

@Data
public class DadosAcordoCancelamentoDTO {
    // É o mesmo que o contId da interface
    private Integer codigoAcordoEfetuado;
    private Integer codigoObjetoInadimplencia;
    private List<Integer> codigosBoletoAcordoNaoPagos;
    private Integer idInterfaceCobranca;
    private String motivoCancelamento;
    private Integer idOperador;
}
