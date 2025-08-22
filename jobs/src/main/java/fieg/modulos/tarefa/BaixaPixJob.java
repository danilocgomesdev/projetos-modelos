package fieg.modulos.tarefa;

import fieg.modulos.cr5.pix.PixClient;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class BaixaPixJob {

    @Inject
    PixClient pixClient;

    // TODO caso esteja pesando o CR5, passar parte da lógica/acessos ao banco/chamadas de endpoint para o Jobs
    public void executaJob() {
        try (Response response = pixClient.validaTransacaoPixAberto()) {
            int status = response.getStatus();
            boolean sucesso = status >= 200 && status < 300;
            String mensagem = sucesso ? "com sucesso" : "com falha";
            Log.infof("BaixaPixJob finalizado %s. Resposta do CR5-Webservices: Status %d. Body: %s", mensagem, status, response.readEntity(String.class));
        }
    }
}
