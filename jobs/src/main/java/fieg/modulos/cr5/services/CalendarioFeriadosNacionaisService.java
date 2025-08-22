package fieg.modulos.cr5.services;

import fieg.core.util.UtilData;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;

@ApplicationScoped
public class CalendarioFeriadosNacionaisService implements ICalendarioFeriadosNacionaisService {

    @Inject
    @RestClient
    Cr5WebservicesRestClient client;

    @Override
    public Date somaDiasUteis(Date data, Integer dias) {
        if (data == null) {
            return null;
        }
        if (dias <= 0) {
            return data;
        }

        try {
            LocalDate doisDiasUteisApos = client.somaDiasUteis(dias, data.getTime());
            return UtilData.localDateParaDate(doisDiasUteisApos);
        } catch (RuntimeException e) {
            Log.warn("Erro ao consultar CR5 para somar dias uteis. Recorrendo a metodo mais simples: " + e.getMessage());

            Date dataSemHoras = UtilData.definirSomentaData(data);
            int diasSomados = 0;

            while (diasSomados < dias) {
                dataSemHoras = UtilData.adicionarDias(dataSemHoras, 1);
                if (!UtilData.verificarFimDeSemana(dataSemHoras)) {
                    diasSomados++;
                }
            }
            return dataSemHoras;
        }
    }
}
