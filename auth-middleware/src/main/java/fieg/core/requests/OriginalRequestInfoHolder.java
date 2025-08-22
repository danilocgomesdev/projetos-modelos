package fieg.core.requests;

import fieg.core.util.RestUtils;
import io.vertx.core.http.HttpMethod;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.UUID;

@RequestScoped
public class OriginalRequestInfoHolder {

    private HttpMethod metodoOriginal;

    private String pathOriginal;

    private String bodyOriginal;

    private MultivaluedMap<String, String> headersOriginais;

    private MultivaluedMap<String, String> queryParamsOriginais;

    private UUID identificador;

    public HttpMethod getMetodoOriginal() {
        return metodoOriginal;
    }

    public void setMetodoOriginal(HttpMethod metodoOriginal) {
        this.metodoOriginal = metodoOriginal;
    }

    public String getPathOriginal() {
        return pathOriginal;
    }

    public void setPathOriginal(String pathOriginal) {
        this.pathOriginal = pathOriginal;
    }

    public String getBodyOriginal() {
        return bodyOriginal;
    }

    public void setBodyOriginal(String bodyOriginal) {
        this.bodyOriginal = bodyOriginal;
    }

    public MultivaluedMap<String, String> getHeadersOriginais() {
        return headersOriginais;
    }

    public void setHeadersOriginais(MultivaluedMap<String, String> headersOriginais) {
        this.headersOriginais = headersOriginais;
    }

    public MultivaluedMap<String, String> getQueryParamsOriginais() {
        return queryParamsOriginais;
    }

    public void setQueryParamsOriginais(MultivaluedMap<String, String> queryParamsOriginais) {
        this.queryParamsOriginais = queryParamsOriginais;
    }

    public UUID getIdentificador() {
        return identificador;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public WebTarget setarQueryParamsNaRequisicao(WebTarget target) {
        var clone = RestUtils.clonaComoMapaDeObjeto(this.queryParamsOriginais);

        for (var key : clone.keySet()) {
            target = target.queryParam(key, clone.get(key).toArray());
        }

        return target;
    }
}
