package fieg.modulos.cr5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelasBaixarProtheusDTO implements Serializable {

    public List<Integer> idCobrancasClientes;
    public String formaPagamento;

}
