package fieg.modulos.cieloJobs.conciliador;

import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ConciliadorBuilder {

    @Any
    @Inject
    Instance<IConciliadorCielo> conciliadoresCielo;

    public Optional<IConciliadorCielo> encontraConciliador(CabecalhoDTO cabecalhoDTO) {
        return conciliadoresCielo
                .stream()
                .filter((conciliador) -> conciliador.seAplicaAoArquivo(cabecalhoDTO))
                .peek((conciliador) -> Log.infof(
                        "Conciliador %s encontrado para arquivo %s",
                        conciliador.getClass().getSimpleName(),
                        cabecalhoDTO.getNomeArquivo()
                ))
                .findFirst();
    }
}
