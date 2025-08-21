package fieg.modulos.unidade.dto.subfiltros;

import fieg.modulos.entidade.enums.Entidade;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FiltroEntidade extends FiltroUnidadeEntidade {

    private final Integer idEntidade;

    public Entidade getEntidade() {
        return Entidade.getByCodigo(idEntidade);
    }
}
