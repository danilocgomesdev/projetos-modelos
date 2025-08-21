package fieg.modulos.cobrancaagrupada.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class AgruparParcelasDTO {

    private List<Integer> listaIdCobrancaCliente;
    private LocalDateTime dataVencimento;
    private Integer idOperadorInclusao;
}
