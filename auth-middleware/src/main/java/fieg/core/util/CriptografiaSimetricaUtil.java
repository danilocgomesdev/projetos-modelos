package fieg.core.util;

import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Optional;

public class CriptografiaSimetricaUtil {

    private final BasicTextEncryptor textEncryptor;

    public CriptografiaSimetricaUtil(String chave) {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(chave);
    }

    public String criptografar(String payload) {
        return textEncryptor.encrypt(payload);
    }

    public Optional<String> descriptografar(String payload) {
        try {
            return Optional.ofNullable(textEncryptor.decrypt(payload));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
