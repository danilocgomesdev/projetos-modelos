package fieg.modulos.cobrancacliente.dto.subfiltros;

import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FiltroTipoIntegraProtheus extends FiltroIntegraProtheus {

    private final List<IntegraProtheus> integraProtheus;
}
