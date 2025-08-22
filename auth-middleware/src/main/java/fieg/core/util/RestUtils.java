package fieg.core.util;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.common.util.CaseInsensitiveMap;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RestUtils {

    public static void removeDeMapa(MultivaluedMap<String, Object> mapa, String... remove) {
        var keys = new HashSet<>(mapa.keySet());
        for (var key : keys) {
            if (Arrays.stream(remove).anyMatch(s -> s.equalsIgnoreCase(key))) {
                mapa.remove(key);
            }
        }
    }

    public static MultivaluedMap<String, Object> clonaComoMapaDeObjeto(MultivaluedMap<String, String> original, String... skip) {
        var clone = new CaseInsensitiveMap<>();

        for (var key : original.keySet()) {
            if (Arrays.stream(skip).anyMatch(s -> s.equalsIgnoreCase(key))) {
                continue;
            }
            clone.put(key, original.get(key).stream().map(str -> (Object) str).collect(Collectors.toList()));
        }

        return clone;
    }

    /**
     * Cria uma cópia profunda do objeto {@code Response} fornecido.
     * <p>
     * Este método é especialmente útil para clonar respostas cuja entidade já foi consumida (lida).
     * Ao chamar este método, todos os cabeçalhos e o status HTTP da resposta original são copiados
     * para uma nova instância de {@code Response}.
     * </p>
     *
     * @param responseOriginal O objeto {@code Response} original que será clonado.
     * @param bodyOriginal O corpo da mensagem da resposta original que será inserido na resposta clonada.
     * @return Um novo objeto {@code Response} que é uma cópia profunda do objeto {@code Response} original.
     */
    public static Response clonaResponse(Response responseOriginal, String bodyOriginal) {
        Response.ResponseBuilder responseClonadaBuilder = Response.status(responseOriginal.getStatus());

        for (var header : responseOriginal.getHeaders().entrySet()) {
            for (var value : header.getValue()) {
                responseClonadaBuilder.header(header.getKey(), value);
            }
        }

        responseClonadaBuilder.entity(bodyOriginal);
        return responseClonadaBuilder.build();
    }

    public static List<String> leTodosOsValoresSeparadosPorVirgula(MultivaluedMap<String, String> headers, String headerName) {
        List<String> valores = headers.get(headerName);
        List<String> resultado = new ArrayList<>();

        if (valores == null || valores.isEmpty()) {
            return resultado;
        }

        for (var valor : valores) {
            resultado.addAll(Arrays.stream(valor.split(",")).map(String::trim).toList());
        }

        return resultado;
    }

    public static List<MediaType> determinaMediaTypes(List<String> mediaTypeString, Consumer<RuntimeException> onError) {
        List<MediaType> resultado = mediaTypeString.stream().map(it -> {
                    try {
                        return MediaType.valueOf(it);
                    } catch (RuntimeException e) {
                        onError.accept(e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (resultado.isEmpty()) {
            return List.of(MediaType.valueOf(MediaType.WILDCARD));
        }

        return resultado;
    }
}
