package fieg.modulos.cadin.dto;

import lombok.Data;

import java.util.List;

@Data
public class AcordosASeremCanceladosDTO {
    private List<DadosAcordoCancelamentoDTO> dados;
    private ParametrosCancelamentoDTO parametrosCancelamento;
}
