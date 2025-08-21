package fieg.modulos.cobrancacliente.dto.subfiltros;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;

import java.util.List;

public abstract sealed class FiltroIntegraProtheus permits FiltroIsProtheus, FiltroTipoIntegraProtheus {

    /**
     * Cria uma instância específica de {@code FiltroIntegraProtheus} baseada nas propriedades
     * presentes no objeto JSON fornecido. Este método deserializa o JSON para uma das subclasses
     * de {@code FiltroIntegraProtheus}, dependendo dos campos especificados. Útil para filtros,
     * pois só se faz sentido usar uma das subclasses, não as duas simultaneamente.
     *
     * <p>Exemplos de uso:</p>
     * <pre>
     * "filtroIntegraProtheus": {
     *     "isProtheus": true
     * }
     * </pre>
     * Este JSON cria uma instância da classe {@code FiltroIsProtheus}.
     *
     * <pre>
     * "filtroIntegraProtheus": {
     *     "integraProtheus": ["VENDA_PARCELADA"]
     * }
     * </pre>
     * Este JSON cria uma instância da classe {@code FiltroTipoIntegraProtheus}.
     *
     * @param node o objeto {@code JsonNode} que representa o JSON de entrada.
     * @return uma instância de uma das subclasses de {@code FiltroIntegraProtheus}, dependendo do conteúdo do JSON.
     * @throws ValorInvalidoException se o JSON não contiver os campos necessários para determinar o tipo de filtro.
     */
    @JsonCreator
    public static FiltroIntegraProtheus create(JsonNode node) throws ValorInvalidoException {
        if (node.has("isProtheus")) {
            return new FiltroIsProtheus(node.get("isProtheus").asBoolean());
        } else if (node.has("integraProtheus")) {
            List<IntegraProtheus> list = IntegraProtheus.convertJsonNodeToList(node.get("integraProtheus"));
            return new FiltroTipoIntegraProtheus(list);
        }

        throw new ValorInvalidoException("Tipo de FiltroIntegraProtheus desconhecido");
    }

}
