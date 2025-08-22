package fieg.core.keycloak.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.core.Form;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestTokenBody {

    public String grantType = "password";
    public String clientId;
    public String responseType = "token";
    public String userName;
    public String password;

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
