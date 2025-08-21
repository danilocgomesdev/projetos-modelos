package fieg.modulos.cobrancaagrupada.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class AgruparParcelasProtheusDTO {

    private List<Integer> listaIdInterfaces;
    private LocalDate dataVencimento;
    private Integer idOperadorInclusao;
}
