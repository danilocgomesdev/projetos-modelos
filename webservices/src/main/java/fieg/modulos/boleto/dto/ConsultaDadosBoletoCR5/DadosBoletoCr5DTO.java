package fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5;

import lombok.Data;

import java.util.List;

@Data
public class DadosBoletoCr5DTO {

    private DadoBoletoCr5DTO dadoBoletoCR5;
    private List<DadosTituloBoletoCr5DTO> dadosTituloBoletoCR5;
}
