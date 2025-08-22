package fieg.core.keycloak.DTO;

import jakarta.ws.rs.core.Form;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTokenBody {

    private final String grantType = "password";
    private final String responseType = "token";

    private String clientId;
    private String userName;
    private String password;

    public RequestTokenBody(String clientId, String userName, String password) {
        this.clientId = clientId;
        this.userName = userName;
        this.password = password;
    }

    public Form getForm() {
        Form form = new Form();
        form.param("grant_type", grantType);
        form.param("client_id", clientId);
        form.param("response_type", responseType);
        form.param("username", userName);
        form.param("password", password);

        return form;
    }

}
