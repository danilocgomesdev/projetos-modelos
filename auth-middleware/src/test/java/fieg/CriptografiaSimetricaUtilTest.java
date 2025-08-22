package fieg;

import fieg.core.util.CriptografiaSimetricaUtil;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

@QuarkusTest
public class CriptografiaSimetricaUtilTest {

    @Test
    void criptografaEDescriptografaCorretamenteComMesmaChave() {
        var payload = "Teste de criptografia";
        var secret = "chave123";

        var criptografado = new CriptografiaSimetricaUtil(secret).criptografar(payload);
        var decriptografado = new CriptografiaSimetricaUtil(secret).descriptografar(criptografado).get();

        Assertions.assertNotEquals(payload, criptografado);
        Assertions.assertEquals(payload, decriptografado);
    }

    @Test
    void naoDescriptografaComChaveDiferente() {
        var payload = "Com chaves diferentes, método deve lançar exceção";

        var criptografado = new CriptografiaSimetricaUtil("chave123").criptografar(payload);

        Assertions.assertNotEquals(payload, criptografado);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            new CriptografiaSimetricaUtil("chave321").descriptografar(criptografado).get();
        });
    }
}
