package fieg.modulo.service.consulta;


import fieg.core.util.AutenticacoService;
import fieg.core.util.XmlToDTO;
import fieg.modulo.client.SOAPRestClient;
import fieg.modulo.dto.ConsultaBoletoDTO;
import fieg.modulo.dto.response.ConsultaBoletoResponseDTO;
import fieg.modulo.dto.HeaderDTO;
import fieg.modulo.dto.resquest.RequestConsultaBoletoDTO;
import fieg.modulo.service.PrepararXmlService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@ApplicationScoped
class BoletoConsultaServiceImpl implements BoletoConsultaService {


    @Inject
    PrepararXmlService prepararXmlService;

    @Inject
    AutenticacoService autenticacaoService;

    @Inject
    @RestClient
    SOAPRestClient soapRestClient;


    @Override
    public ConsultaBoletoResponseDTO consultaBoleto(RequestConsultaBoletoDTO dto) {

        String autenticaoHash = autenticacaoService.gerarHash(Integer.parseInt(dto.getCodigoBeneficiario()), "00000000",
                "000000000000000", dto.getNossoNumero(), dto.getCpfCnpj());
        HeaderDTO header = prepararXmlService.gerarHeader(autenticaoHash);
        ConsultaBoletoDTO params = new ConsultaBoletoDTO();
        params.setHeader(header);
        params.setCodigoBeneficiario(dto.getCodigoBeneficiario());
        params.setNossoNumero(dto.getNossoNumero());
        String xmlConsultaBoleto = prepararXmlService.gerarXmlTemplateConsultaBoleto(params);
        String res = soapRestClient.consultaBoleto(xmlConsultaBoleto);

        return XmlToDTO.transformeDTO(res, ConsultaBoletoResponseDTO.class);


    }

}
