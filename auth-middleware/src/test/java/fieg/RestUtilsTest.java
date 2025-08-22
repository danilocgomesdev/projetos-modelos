package fieg;

import fieg.core.util.RestUtils;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.reactive.common.util.CaseInsensitiveMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class RestUtilsTest {

    @Test
    public void clonaComoMapaDeObjetoClonaTudo() {
        MultivaluedMap<String, String> params = new CaseInsensitiveMap<>() {
            {
                putSingle("chave1", "valor 1");
                putSingle("chave2", "valor 2");
                putSingle("chave3", "valor 3");
            }
        };

        MultivaluedMap<String, Object> result = RestUtils.clonaComoMapaDeObjeto(params);

        for (var key : params.keySet()) {
            assertEquals(params.get(key), result.get(key));
        }
        assertEquals(result.size(), params.size());
    }

    @Test
    public void clonaComoMapaDeObjetoClonaVazio() {
        MultivaluedMap<String, String> params = new CaseInsensitiveMap<>();
        MultivaluedMap<String, Object> result = RestUtils.clonaComoMapaDeObjeto(params);
        assertTrue(result.isEmpty());
    }

    @Test
    public void clonaComoMapaDeObjetoExcluiCorretamente() {
        MultivaluedMap<String, String> params = new CaseInsensitiveMap<>() {
            {
                putSingle("cHave1", "valor 1");
                putSingle("chAve2", "valor 2");
                putSingle("chAve3", "valor 3");
                putSingle("chavE4", "valor 4");
            }
        };

        MultivaluedMap<String, Object> result = RestUtils.clonaComoMapaDeObjeto(params, "chave1", "ChavE3");

        for (var key : result.keySet()) {
            assertEquals(params.get(key), result.get(key));
        }
        assertEquals(result.size(), params.size() - 2);
    }

    @Test
    public void clonaComoMapaDeObjeto_TodasAsChaves() {
        MultivaluedMap<String, String> original = new CaseInsensitiveMap<>();
        original.putSingle("chave1", "valor1");
        original.putSingle("chave2", "valor2");

        MultivaluedMap<String, Object> clone = RestUtils.clonaComoMapaDeObjeto(original);

        assertEquals(2, clone.size());
        assertTrue(clone.containsKey("chave1"));
        assertTrue(clone.containsKey("chave2"));
    }

    @Test
    public void clonaComoMapaDeObjeto_TodasAsChavesOmitidas() {
        MultivaluedMap<String, String> original = new CaseInsensitiveMap<>();
        original.putSingle("chave1", "valor1");
        original.putSingle("chave2", "valor2");

        MultivaluedMap<String, Object> clone = RestUtils.clonaComoMapaDeObjeto(original, "chave1", "chave2");

        assertTrue(clone.isEmpty());
    }

    @Test
    public void clonaComoMapaDeObjeto_MapaVazio() {
        MultivaluedMap<String, String> original = new CaseInsensitiveMap<>();

        MultivaluedMap<String, Object> clone = RestUtils.clonaComoMapaDeObjeto(original);

        assertTrue(clone.isEmpty());
    }
    @Test
    public void testLeTodosOsValoresSeparadosPorVirgula_MultiplosValores() {
        MultivaluedMap<String, String> headers = new CaseInsensitiveMap<>();
        headers.put("TestHeader", List.of("value1,value2,value3", "value4"));
        List<String> result = RestUtils.leTodosOsValoresSeparadosPorVirgula(headers, "TestHeader");
        assertEquals(Arrays.asList("value1", "value2", "value3", "value4"), result);
    }

    @Test
    public void testLeTodosOsValoresSeparadosPorVirgula_ValorUnico() {
        MultivaluedMap<String, String> headers = new CaseInsensitiveMap<>();
        headers.add("TestHeader", "value1");
        List<String> result = RestUtils.leTodosOsValoresSeparadosPorVirgula(headers, "TestHeader");
        assertEquals(Arrays.asList("value1"), result);
    }

    @Test
    public void testLeTodosOsValoresSeparadosPorVirgula_ValorVazio() {
        MultivaluedMap<String, String> headers = new CaseInsensitiveMap<>();
        headers.add("TestHeader", "");
        List<String> result = RestUtils.leTodosOsValoresSeparadosPorVirgula(headers, "TestHeader");
        assertEquals(Arrays.asList(""), result);
    }

    @Test
    public void testLeTodosOsValoresSeparadosPorVirgula_HeaderInexistente() {
        MultivaluedMap<String, String> headers = new CaseInsensitiveMap<>();
        List<String> result = RestUtils.leTodosOsValoresSeparadosPorVirgula(headers, "TestHeader");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testLeTodosOsValoresSeparadosPorVirgula_ValoresComEspacos() {
        MultivaluedMap<String, String> headers = new CaseInsensitiveMap<>();
        headers.add("TestHeader", "value1, value2 , value3");
        List<String> result = RestUtils.leTodosOsValoresSeparadosPorVirgula(headers, "TestHeader");
        assertEquals(Arrays.asList("value1", "value2", "value3"), result);
    }

    @Test
    public void testDeterminaMediaTypes_VariosTiposValidos() {
        List<String> mediaTypeStrings = Arrays.asList("application/json", "text/plain");
        List<MediaType> result = RestUtils.determinaMediaTypes(mediaTypeStrings, e -> {
        });
        assertEquals(2, result.size());
        assertTrue(result.contains(MediaType.valueOf("application/json")));
        assertTrue(result.contains(MediaType.valueOf("text/plain")));
    }

    @Test
    public void testDeterminaMediaTypes_AlgunsTiposInvalidos() {
        List<String> mediaTypeStrings = Arrays.asList("application/json", "invalid");
        List<MediaType> result = RestUtils.determinaMediaTypes(mediaTypeStrings, e -> {
        });
        assertEquals(1, result.size());
        assertTrue(result.contains(MediaType.valueOf("application/json")));
    }

    @Test
    public void testDeterminaMediaTypes_ListaVazia() {
        List<String> mediaTypeStrings = List.of();
        List<MediaType> result = RestUtils.determinaMediaTypes(mediaTypeStrings, e -> {
        });
        assertEquals(1, result.size());
        assertTrue(result.contains(MediaType.valueOf(MediaType.WILDCARD)));
    }

    @Test
    public void testDeterminaMediaTypes_OnErrorCalled() {
        List<String> mediaTypeStrings = Arrays.asList("invalid1", "invalid2");
        int[] counter = new int[]{0};
        Consumer<RuntimeException> onError = e -> counter[0]++;
        List<MediaType> result = RestUtils.determinaMediaTypes(mediaTypeStrings, onError);
        assertEquals(1, result.size());
        assertTrue(result.contains(MediaType.valueOf(MediaType.WILDCARD)));
        assertEquals(2, counter[0]);
    }
}
