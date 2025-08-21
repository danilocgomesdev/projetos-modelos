package fieg.modulos.cobrancacliente.dto.subfiltros;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FiltroEstaPago extends FiltroPagamento {

    private final boolean isPago;
}
