package fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CobrancaClienteGrupoDTO {
    private List<Integer> idsCobrancasClientes;
    private Integer idCobrancasAgrupada; // Pode ser null
}
