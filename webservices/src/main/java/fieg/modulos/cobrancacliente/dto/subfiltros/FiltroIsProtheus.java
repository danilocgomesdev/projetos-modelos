package fieg.modulos.cobrancacliente.dto.subfiltros;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FiltroIsProtheus extends FiltroIntegraProtheus {

    private final boolean isProtheus;
}
