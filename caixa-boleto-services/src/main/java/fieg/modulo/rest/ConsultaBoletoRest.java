package fieg.modulo.rest;

import fieg.modulo.dto.response.ConsultaBoletoResponseDTO;
import fieg.modulo.dto.response.ManutencaoBoletoResponseDTO;
import fieg.modulo.dto.resquest.RequestBaixarBoletoDTO;
import fieg.modulo.dto.resquest.RequestConsultaBoletoDTO;
import fieg.modulo.dto.resquest.RequestManutencaoBoletoDTO;
import fieg.modulo.service.consulta.BoletoConsultaService;
import fieg.modulo.service.manutencao.BoletoManutencaoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/boleto")
@Authenticated
public class ConsultaBoletoRest {

    @Inject
    BoletoConsultaService consultaBoleto;

    @Inject
    BoletoManutencaoService boletoManutencaoService;

    @POST
    @Path("/consulta")
    public ConsultaBoletoResponseDTO consultaBoleto(RequestConsultaBoletoDTO consultaBoletoDTO) {
        return consultaBoleto.consultaBoleto(consultaBoletoDTO);
    }

    @POST
    @Path("/inclui")
    public ManutencaoBoletoResponseDTO incluirBoleto(RequestManutencaoBoletoDTO dto) {
        return boletoManutencaoService.incluirBoleto(dto);
    }

    @POST
    @Path("/altera")
    public ManutencaoBoletoResponseDTO alterarBoleto(RequestManutencaoBoletoDTO dto) {
        return boletoManutencaoService.alterarBoleto(dto);
    }

    @POST
    @Path("/baixa")
    public ManutencaoBoletoResponseDTO baixarBoleto(RequestBaixarBoletoDTO dto) {
        return boletoManutencaoService.baixarBoleto(dto);
    }

}
