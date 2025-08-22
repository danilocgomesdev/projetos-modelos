package fieg.modulos.cr5.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgrupamentoCobrancaAutomaticaEmRedeDTO {

    private Integer idOperador;
    private Date dataVencimentoAgrupamento;
    private List<Integer> interfacesCobrancas;
}
