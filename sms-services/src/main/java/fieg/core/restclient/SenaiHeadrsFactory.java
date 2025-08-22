package fieg.core.restclient;


import org.apache.commons.codec.binary.Base64;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.nio.charset.Charset;

@ApplicationScoped
public class SenaiHeadrsFactory implements ClientHeadersFactory{

    @ConfigProperty(name = "zenvia/mp-rest/user_senai")
    String userZenviaSenai;

    @ConfigProperty(name = "zenvia/mp-rest/senha_senai")
    String senhaZenviaSenai;


    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> outgoingHeaders) {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>(outgoingHeaders);
        headers.putSingle("Authorization", String.format("Basic %s", encodeBasicauthetication(userZenviaSenai, senhaZenviaSenai)));
        return headers;
    }

    private String encodeBasicauthetication(String user, String senha) {
        String auth = user + ":" + senha;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        return new String(encodedAuth);
    }
}