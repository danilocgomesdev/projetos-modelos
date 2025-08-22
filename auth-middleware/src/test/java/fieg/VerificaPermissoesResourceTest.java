package fieg;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class VerificaPermissoesResourceTest {

    // TODO escrever mais testes
    @Test
    public void semTokenRetorna401() {
        given()
                .when().get("/api/verifica-permissoes")
                .then()
                .statusCode(401);
    }

}