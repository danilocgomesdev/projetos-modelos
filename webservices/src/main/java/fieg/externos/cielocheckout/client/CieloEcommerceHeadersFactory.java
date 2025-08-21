package fieg.externos.cielocheckout.client;

import fieg.core.exceptions.InconsistenciaInternaException;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.util.UtilString;
import fieg.externos.cielocheckout.dto.CieloEcommerceToken;
import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.estabelecimentocielo.enums.MeioPagamentoEstabelecimentoCielo;
import fieg.modulos.estabelecimentocielo.model.EstabelecimentoCielo;
import fieg.modulos.estabelecimentocielo.repository.EstabelecimentoCieloRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.util.Base64;
import java.util.List;

public class CieloEcommerceHeadersFactory implements ClientHeadersFactory {

    @Inject
    @RestClient
    CieloEcommerceTokenClient cieloEcommerceTokenClient;

    @Inject
    EstabelecimentoCieloRepository estabelecimentoCieloRepository;

    CieloEcommerceToken responseToken = null;

    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> outgoingHeaders
    ) {
        var headers = new MultivaluedHashMap<>(outgoingHeaders);
        List<String> codigoEntidade = headers.remove("codigo-entidade");

        if (codigoEntidade == null || codigoEntidade.isEmpty()) {
            throw new IllegalStateException("Necessário informar a entidade pelo header codigo-entidade para usar a API Cielo Ecommerce");
        }

        Entidade entidade = Entidade.getByCodigo(codigoEntidade.get(0));
        EstabelecimentoCielo estabelecimentoCielo = estabelecimentoCieloRepository
                .getByEntidadeAndMeioPagamento(entidade, MeioPagamentoEstabelecimentoCielo.ECOMMERCE)
                .orElseThrow(() -> new ValorInvalidoException("Não existe Estabelecimento Cielo Ecommerce registrado para a entidade " + entidade));

        if (UtilString.isBlank(estabelecimentoCielo.getClientSecretCheckout())) {
            throw new InconsistenciaInternaException("Estabelecimento ECOMMERCE da entidade %d não tem Client Secret do chekout cadastrada!".formatted(entidade.codigo));
        }
        if (UtilString.isBlank(estabelecimentoCielo.getMerchantIdCheckout())) {
            throw new InconsistenciaInternaException("Estabelecimento ECOMMERCE da entidade %d não tem Merchant ID do chekout cadastrado!".formatted(entidade.codigo));
        }

        if (responseToken == null || responseToken.getExpiresAt().isBefore(Instant.now())) {
            String authValue = estabelecimentoCielo.getMerchantIdCheckout() + ":" + estabelecimentoCielo.getClientSecretCheckout();
            String encodedAuthValue = "Basic " + Base64.getEncoder().encodeToString(authValue.getBytes());
            responseToken = cieloEcommerceTokenClient.getToken(encodedAuthValue);
        }

        headers.putSingle("Authorization", "Bearer " + responseToken.getToken());
        headers.putSingle("MerchantId", estabelecimentoCielo.getMerchantIdCheckout());

        return headers;
    }
}
