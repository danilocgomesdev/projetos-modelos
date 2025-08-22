package fieg.core.producers;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.ws.rs.Produces;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

@ApplicationScoped
public class RedissonClientProducer {

    @ConfigProperty(name = "redis.host")
    String host;

    @ConfigProperty(name = "redis.password", defaultValue = " ") // tem que ter um espaço
    String password;

    @Produces
    @Singleton
    public RedissonClient createRedisson() throws UnknownHostException {
        try {
            return criaClient(host);
        } catch (Exception ignored) {
            Log.warnf("Erro ao criar Redisson pelo host %s. Tentando resolver manualmente e criar pelo IP", host);
            // Solução de contorno. Redisson não consegue identificar o host AWS pelo nome.
            var regexPorta = Pattern.compile(":\\d+$");
            var matcherPorta = regexPorta.matcher(host);

            var porta = "";
            if (matcherPorta.find()) {
                porta = matcherPorta.group();
            }

            var regexProtocolo = Pattern.compile("^\\w+://");
            var matcherProtocolo = regexProtocolo.matcher(host);
            matcherProtocolo.find();
            var protocolo = matcherProtocolo.group();

            var endereco = InetAddress.getByName(host.replace(protocolo, "").replace(porta, ""));

            Log.infof("IP resolvido: %s", endereco.getHostAddress());

            return criaClient(protocolo + endereco.getHostAddress() + porta);
        }
    }

    private RedissonClient criaClient(String host) {
        var config = new Config();

        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(host);

        if (!password.trim().isEmpty()) {
            singleServerConfig.setPassword(password);
        }

        return Redisson.create(config);
    }
}
