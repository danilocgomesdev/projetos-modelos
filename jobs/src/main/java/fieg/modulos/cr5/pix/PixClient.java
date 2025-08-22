package fieg.modulos.cr5.pix;

import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionException;

@ApplicationScoped
public class PixClient {

    @Inject
    @RestClient
    Cr5WebservicesRestClient restClient;

    public Response validaTransacaoPixAberto() {
        Response response;
        try {
            response = restClient.validaTransacaoPixAberto();
        } catch (CompletionException e) {
            // Captura CompletionException e verifica a causa
            if (e.getCause() instanceof WebApplicationException) {
                response = ((WebApplicationException) e.getCause()).getResponse();
            } else {
                throw e; // Se a causa não for WebApplicationException, relança a exceção
            }
        } catch (WebApplicationException e) {
            response = e.getResponse();
        }

        return response;
    }
}
