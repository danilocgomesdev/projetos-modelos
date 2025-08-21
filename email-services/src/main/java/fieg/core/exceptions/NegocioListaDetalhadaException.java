package fieg.core.exceptions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.smallrye.openapi.runtime.io.JsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class NegocioListaDetalhadaException extends ApplicationExceptionBase {

    private final List<DadoErro> erroList = new ArrayList<>();

    public NegocioListaDetalhadaException() {
        super("Um ou mais erros encontrados. Ver lista de detalhes (listaDetalhes).", CodigoErro.REGRA_DE_NEGOCIO);
    }

    public void adicionaDado(String item, String mensagemErro) {
        this.erroList.add(new DadoErro(item, mensagemErro));
    }

    private record DadoErro(String item, String mensagemErro) {
    }

    @Override
    public void addToJsonResponse(ObjectNode json) {
        ArrayNode jsonArray = JsonUtil.arrayNode();
        for (DadoErro dadoErro : getErroList()) {
            jsonArray.addObject()
                    .put("item", dadoErro.item())
                    .put("mensagem", dadoErro.mensagemErro());
        }
        json.set("listaDetalhes", jsonArray);
    }
}
