package fieg.modulos.cr5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgrupamentoCobrancaAutomaticaDTO {

    private Integer contId;
    private Integer sistemaId;
    private Integer operadorId;
    private Date dataVencimentoAgrupamento;
    private Integer numeroParcelas;
}

