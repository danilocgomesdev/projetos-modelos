package fieg.core.restclient;


import org.apache.commons.codec.binary.Base64;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.nio.charset.Charset;

@ApplicationScoped
public class SesiHeadrsFactory implements ClientHeadersFactory{


    @ConfigProperty(name = "zenvia/mp-rest/user_sesi")
    String userZenviaSesi;

    @ConfigProperty(name = "zenvia/mp-rest/senha_sesi")
    String senhaZenviaSesi;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> outgoingHeaders) {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>(outgoingHeaders);
        headers.putSingle("Authorization", String.format("Basic %s", encodeBasicauthetication(userZenviaSesi, senhaZenviaSesi)));
        return headers;
    }

    private String encodeBasicauthetication(String user, String senha) {
        String auth = user + ":" + senha;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        return new String(encodedAuth);
    }
}