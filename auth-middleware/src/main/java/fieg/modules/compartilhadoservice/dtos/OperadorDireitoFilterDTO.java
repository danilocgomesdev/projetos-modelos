package fieg.modules.compartilhadoservice.dtos;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OperadorDireitoFilterDTO {
    @QueryParam("operador")
    public Integer idOperador;

    @QueryParam("sistema")
    public Integer sistema;
}

