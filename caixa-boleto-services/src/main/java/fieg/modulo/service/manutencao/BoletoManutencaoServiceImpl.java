package fieg.modulo.service.manutencao;

import fieg.core.util.AutenticacoService;
import fieg.core.util.XmlToDTO;
import fieg.modulo.client.SOAPRestClient;
import fieg.modulo.dto.*;
import fieg.modulo.dto.response.ConsultaBoletoResponseDTO;
import fieg.modulo.dto.response.ManutencaoBoletoResponseDTO;
import fieg.modulo.dto.resquest.RequestBaixarBoletoDTO;
import fieg.modulo.dto.resquest.RequestManutencaoBoletoDTO;
import fieg.modulo.service.PrepararXmlService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.ZoneId;

@ApplicationScoped
class BoletoManutencaoServiceImpl implements BoletoManutencaoService {

    @Inject
    AutenticacoService autenticacaoService;

    @Inject
    PrepararXmlService prepararXmlService;

    @Inject
    @RestClient
    SOAPRestClient soapRestClient;

    @Override
    public ManutencaoBoletoResponseDTO incluirBoleto(RequestManutencaoBoletoDTO dto) {
        String autenticacaoHash = gerarAutenticacaoHash(dto);
        HeaderDTO header = prepararXmlService.gerarHeader(autenticacaoHash);

        IncluiBoletoDTO params = new IncluiBoletoDTO();
        params.setTitulo(dto.getTitulo());
        params.setHeader(header);
        params.setCodigoBeneficiario(dto.getCodigoBeneficiario());
        String xmlIncluirBoleto = prepararXmlService.gerarXmlTemplateIncluirBoleto(params);
        String res = soapRestClient.incluirBoleto(xmlIncluirBoleto);
        return XmlToDTO.transformeDTO(res, ManutencaoBoletoResponseDTO.class);
    }

    @Override
    public ManutencaoBoletoResponseDTO alterarBoleto(RequestManutencaoBoletoDTO dto) {
        String autenticacaoHash = gerarAutenticacaoHash(dto);
        HeaderDTO header = prepararXmlService.gerarHeader(autenticacaoHash);

        AlteraBoletoDTO params = new AlteraBoletoDTO();
        params.setTitulo(dto.getTitulo());
        params.setHeader(header);
        params.setCodigoBeneficiario(dto.getCodigoBeneficiario());
        String xmlIncluirBoleto = prepararXmlService.gerarXmlTemplateAlteraBoleto(params);
        String res = soapRestClient.incluirBoleto(xmlIncluirBoleto);
        return XmlToDTO.transformeDTO(res, ManutencaoBoletoResponseDTO.class);
    }

    @Override
    public ManutencaoBoletoResponseDTO baixarBoleto(RequestBaixarBoletoDTO dto) {
        String autenticaoHash = autenticacaoService.gerarHash(Integer.parseInt(dto.getCodigoBeneficiario()), "00000000",
                "000000000000000", dto.getNossoNumero(), dto.getCpfCnpj());
        HeaderDTO header = prepararXmlService.gerarHeader(autenticaoHash);
        BaixaBoletoDTO params = new BaixaBoletoDTO();
        params.setHeader(header);
        params.setCodigoBeneficiario(dto.getCodigoBeneficiario());
        params.setNossoNumero(dto.getNossoNumero());
        String xmlConsultaBoleto = prepararXmlService.gerarXmlTemplateBaixaBoleto(params);
        String res = soapRestClient.baixarBoleto(xmlConsultaBoleto);
        return XmlToDTO.transformeDTO(res, ManutencaoBoletoResponseDTO.class);
    }


    private String gerarAutenticacaoHash(RequestManutencaoBoletoDTO dto) {
        return autenticacaoService.gerarHash(
                Integer.parseInt(dto.getCodigoBeneficiario()),
                autenticacaoService.getDataConvertida(dto.getTitulo().getDataVencimento().atStartOfDay().atZone(ZoneId.systemDefault()).toLocalDateTime()),
                String.valueOf(dto.getTitulo().getValor()),
                Long.parseLong(dto.getTitulo().getNossoNumero()),
                dto.getCpfCnpjBeneficiario()
        );
    }

}
