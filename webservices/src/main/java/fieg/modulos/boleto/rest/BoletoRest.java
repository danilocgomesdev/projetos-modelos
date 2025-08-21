package fieg.modulos.boleto.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.caixaboletoservice.consulta.dto.*;
import fieg.externos.caixaboletoservice.consulta.service.CaixaBoletoService;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.boleto.dto.BoletoDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.ControleNegocialDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoNossoNumeroResponseDTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadosBoletoCr5DTO;
import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.boleto.repository.BoletoRepository;
import fieg.modulos.boleto.service.BoletoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;

@Authenticated
@Path("/boleto")
@Tag(name = "Boleto")
@PermissaoNecessaria({Acessos.IMPRIMIR_BOLETO})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoletoRest {

    @Inject
    BoletoService boletoService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    Mapper<Boleto, BoletoDTO> responseMapper;

    @Inject
    CaixaBoletoService caixaBoletoService;

    @Inject
    BoletoRepository boletoRepository;

    @GET
    @Path("/{idBoleto}")
    @Operation(summary = "Busca boleto pelo id")
    public BoletoDTO getBoletoById(
            @Parameter(description = "Id do Boleto", example = "99")
            @PathParam("idBoleto") @NotNull Integer idBoleto) {
        return boletoService.getById(idBoleto)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Boleto não encontrado"));
    }

    @POST
    @Path("/consulta-boleto-cr5")
    @Operation(summary = "Consulta Boleto no Sistema CR5")
    public DadosBoletoCr5DTO getConsultaBoletoCR5(@RequestBody BoletoFilterDTO consultaBoletoFilterDTO) {
        return boletoService.consultaDadosBoleto(consultaBoletoFilterDTO);
    }

    @POST
    @Path("/consulta-boleto-caixa")
    @Operation(summary = "Consulta Boleto no Site da CAIXA")
    public ConsultaBoletoCaixaResponseDTO getConsultaBoletoCaixa(@RequestBody BoletoFilterDTO consultaBoletoFilterDTO) {

        ConsultaBoletoCaixaResponseDTO consultaBoletoResponseDTO = new ConsultaBoletoCaixaResponseDTO() ;


        try {

           Optional<ConsultaBoletoNossoNumeroResponseDTO> consultaBoletoNossoNumeroResponseDTO = boletoRepository.pesquisaUsandoNossoNumero(consultaBoletoFilterDTO.getNossoNumero().toString());

           if (!consultaBoletoNossoNumeroResponseDTO.isEmpty() && consultaBoletoNossoNumeroResponseDTO != null) {

               consultaBoletoFilterDTO.setCodigoBeneficiario(consultaBoletoNossoNumeroResponseDTO.get().getCodigoBeneficiario());
               consultaBoletoFilterDTO.setCpfCnpj(consultaBoletoNossoNumeroResponseDTO.get().getCpfCnpjCedente());
               consultaBoletoFilterDTO.setNossoNumero(consultaBoletoNossoNumeroResponseDTO.get().getNossoNumero());

               consultaBoletoResponseDTO = caixaBoletoService.consultaBoletoCaixa(consultaBoletoFilterDTO);

           } else {

              BodyDTO bodyDTO = new BodyDTO() ;
              ServicoSaidaDTO servicoSaidaDTO = new ServicoSaidaDTO();
              HeaderDTO headerDTO = new HeaderDTO() ;
              ConsultaBoletoDTO consultaBoletoDTO = new ConsultaBoletoDTO();
              ControleNegocialDTO controleNegocialDTO = new ControleNegocialDTO();
              DadosDTO dadosDTO = new DadosDTO();

              servicoSaidaDTO.setHeader(headerDTO);
              servicoSaidaDTO.setCodRetorno("00");
              servicoSaidaDTO.setMsgRetorno("");
              servicoSaidaDTO.setOrigemRetorno("CONSULTA_BANCARIA");
              servicoSaidaDTO.setDados(dadosDTO);

              dadosDTO.setConsultaBoleto(null);
              dadosDTO.setControleNegocial(null);

              headerDTO.setVersao("3.0");
              headerDTO.setAutenticacao("");
              headerDTO.setUsuarioServico("");
              headerDTO.setOperacao("CONSULTA_BOLETO");
              headerDTO.setSistemaOrigem("");
              headerDTO.setDataHora("");

              bodyDTO.setServicoSaida(servicoSaidaDTO);

              consultaBoletoResponseDTO.setBody(bodyDTO);

           }

       } catch (WebApplicationException e) {
            throw new NegocioException(" Boleto não encontrado .") ;
        }

        return consultaBoletoResponseDTO;

    }

    @GET
    @Path("/{nossoNumero}")
    @Operation(summary = "Busca boleto pelo nosso numero")
    public Optional<ConsultaBoletoNossoNumeroResponseDTO> getBoletoById(
            @Parameter(description = "Nosso numero", example = "14400500000008206")
            @PathParam("nossoNumero") @NotNull String nossoNumero) {
        return boletoRepository.pesquisaUsandoNossoNumero(nossoNumero) ;

    }

    @POST
    @Path("/retirar-vinculo/{idBoleto}")
    public Response retirarVinculo(@PathParam("idBoleto") Integer idBoleto) {
        Integer idOperador = requestInfoHolder.getIdOperador().orElse(null);
        boletoService.retiraVinculoBoleto(idBoleto, idOperador);
        return Response.ok().build();
    }
}