package fieg.core.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

public class RestClientUtil {

    /**
     * Normalmente, ao utilizar o cliente do Quarkus, uma execeção é lançada caso seja retornado código fora do range
     * [200, 299], mas, muitas vezes, desejamos obter o objeto {@link Response} de toda forma. Portanto, esse método
     * captura as exceções lançadas por status de erro e retorna a resposta pura.
     *
     * @param fazRequest método que executa a requisição em si
     * @return o objeto de Resposta, sendo código de sucesso ou não. Você deve verificar o sucesso manualmente.
     */
    public static Response responseIgnorandoErro(Supplier<Response> fazRequest) {
        Response response;
        try {
            response = fazRequest.get();
        } catch (CompletionException e) {
            if (e.getCause() instanceof WebApplicationException) {
                response = ((WebApplicationException) e.getCause()).getResponse();
            } else {
                throw e; // Se não for uma WebApplicationException, relança a exceção
            }
        } catch (WebApplicationException e) {
            response = e.getResponse();
        }

        return response;
    }
}
