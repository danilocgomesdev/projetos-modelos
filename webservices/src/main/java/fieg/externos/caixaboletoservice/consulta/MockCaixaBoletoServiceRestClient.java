package fieg.externos.caixaboletoservice.consulta;

import fieg.core.exceptions.NegocioException;
import fieg.externos.caixaboletoservice.baixa.dto.ManutencaoBoletoResponseDTO;
import fieg.externos.caixaboletoservice.consulta.dto.ConsultaBoletoCaixaResponseDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class MockCaixaBoletoServiceRestClient implements CaixaBoletoServiceRestClient {


    @Inject
    @RestClient
    CaixaBoletoServiceRestClient restClient;

    @Override
    public ConsultaBoletoCaixaResponseDTO consultaBoletoCaixa(BoletoFilterDTO dto) {
        return restClient.consultaBoletoCaixa(dto);
    }

    @Override
    public ManutencaoBoletoResponseDTO baixaBoletoCaixa(BoletoFilterDTO dto) {
        if (!"prod".equals(ProfileManager.getActiveProfile())) {
            return new ManutencaoBoletoResponseDTO(); // Retorne um objeto válido de teste
        }

        ManutencaoBoletoResponseDTO response = restClient.baixaBoletoCaixa(dto);

        if (response.getBody() != null) {
            return response;
        } else {
            throw new NegocioException("Erro ao baixar boleto: resposta nula");
        }
    }

}
