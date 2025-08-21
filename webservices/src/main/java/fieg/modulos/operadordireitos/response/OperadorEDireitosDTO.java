package fieg.modulos.operadordireitos.response;

import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class OperadorEDireitosDTO {


    private PessoaCIDTO pessoa;

    private List<OperadorDireitoDTO> direitos;


}
