package fieg.modulos.cobrancacliente.dto.subfiltros;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.util.UtilConversao;

import java.time.LocalDate;

public abstract sealed class FiltroPagamento permits FiltroDataPagamento, FiltroEstaPago {

    /**
     * Cria uma instância específica de {@code FiltroPagamento} com base nas propriedades
     * presentes no objeto JSON fornecido. Este método deserializa o JSON para uma das subclasses
     * de {@code FiltroPagamento}, dependendo dos campos especificados.
     *
     * <p>Exemplos de uso:</p>
     * <pre>
     * "filtroPagamento": {
     *     "isPago": true
     * }
     * </pre>
     * Este JSON cria uma instância da classe {@code FiltroEstaPago}, indicando que a consulta
     * deverá retornar apenas pagamentos que já foram realizados.
     *
     * <pre>
     * "filtroPagamento": {
     *     "dataPagamentoInicial": "2024-01-01",
     *     "dataPagamentoFinal": "2024-01-31"
     * }
     * </pre>
     * Este JSON cria uma instância da classe {@code FiltroDataPagamento}, especificando um intervalo
     * de datas para buscar pagamentos realizados entre as datas fornecidas.
     *
     * @param node o objeto {@code JsonNode} que representa o JSON de entrada.
     * @return uma instância de uma das subclasses de {@code FiltroPagamento}, dependendo do conteúdo do JSON.
     * @throws ValorInvalidoException se o JSON não contiver os campos necessários para determinar o tipo de filtro
     *                                ou se as datas fornecidas forem inválidas.
     */
    @JsonCreator
    public static FiltroPagamento create(JsonNode node) throws ValorInvalidoException {
        if (node.has("isPago")) {
            return new FiltroEstaPago(node.get("isPago").asBoolean());
        } else if (node.has("dataPagamentoInicial") || node.has("dataPagamentoFinal")) {
            LocalDate dataInicial = UtilConversao.getDateFromNode(node, "dataPagamentoInicial");
            LocalDate dataFinal = UtilConversao.getDateFromNode(node, "dataPagamentoFinal");

            return new FiltroDataPagamento(dataInicial, dataFinal);
        }

        throw new ValorInvalidoException("Tipo de FiltroPagamento desconhecido");
    }

}
