package fieg.modulos.unidade.dto.subfiltros;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FiltroUnidade extends FiltroUnidadeEntidade {

    private final Integer idUnidade;
}
