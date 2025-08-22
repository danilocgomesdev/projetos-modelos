package util;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class TesteUtil {

    public File leArquivoDeTeste(String caminhoRelativo) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(caminhoRelativo).getFile());
    }

    public Date dateOf(int ano, int mes, int dia) {
        return Date.from(LocalDate.of(ano, mes, dia).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
