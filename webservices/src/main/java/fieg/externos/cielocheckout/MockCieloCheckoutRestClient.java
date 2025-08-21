package fieg.externos.cielocheckout;

import fieg.externos.cielocheckout.client.CieloEcommerceClient;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCielo;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCieloUpdateDTO;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class MockCieloCheckoutRestClient implements CieloEcommerceClient {

    @Inject
    @RestClient
    CieloEcommerceClient restClient;

    @Override
    public PagamentoRecorrenteCielo consultaPagamentoRecorrente(Integer codigoEntidade, String idRecorrencia) {
        return restClient.consultaPagamentoRecorrente(codigoEntidade, idRecorrencia);
    }

    @Override
    public Response desativaPagamentoRecorrente(Integer codigoEntidade, String idRecorrencia) {
        if (!"prod".equals(ProfileManager.getActiveProfile())) {
            return Response.ok().build();
        }
        return restClient.desativaPagamentoRecorrente(codigoEntidade, idRecorrencia);
    }

    @Override
    public String atualizaPagamentoRecorrente(Integer codigoEntidade, PagamentoRecorrenteCieloUpdateDTO dto) {
        if (!"prod".equals(ProfileManager.getActiveProfile())) {
            return "";
        }
        return restClient.atualizaPagamentoRecorrente(codigoEntidade, dto);
    }

}
