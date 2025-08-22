package fieg.core.util;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@ApplicationScoped
public class GerarArquivosWsdl {


    public void geraConsultaCobrancaBancaria() throws IOException {
        // URL do serviço de consulta WSDL
        String wsdlUrl = "https://barramento.caixa.gov.br/sibar/ConsultaCobrancaBancaria/Boleto?wsdl";

        // Nome do arquivo WSDL local temporário
        String tempWsdlFile = "consultaCobrancaBancaria.wsdl";

        // Baixar o arquivo WSDL
        URL url = new URL(wsdlUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (InputStream in = connection.getInputStream(); FileOutputStream out = new FileOutputStream(tempWsdlFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    public void geraManutencaoCobrancaBancaria() throws IOException {
        // URL do serviço de consulta WSDL
        String wsdlUrl = "https://barramento.caixa.gov.br/sibar/ManutencaoCobrancaBancaria/Boleto/Externo?wsdl";

        // Nome do arquivo WSDL local temporário
        String tempWsdlFile = "manutencaoCobrancaBancaria.wsdl";

        // Baixar o arquivo WSDL
        URL url = new URL(wsdlUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (InputStream in = connection.getInputStream(); FileOutputStream out = new FileOutputStream(tempWsdlFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
