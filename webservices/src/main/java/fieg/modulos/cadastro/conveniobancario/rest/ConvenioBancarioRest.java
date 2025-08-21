package fieg.modulos.cadastro.conveniobancario.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.conveniobancario.dto.*;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;
import fieg.modulos.cadastro.conveniobancario.model.FaixaNossoNumero;
import fieg.modulos.cadastro.conveniobancario.service.ConvenioBancarioService;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import fieg.modulos.visao.visaounidade.repository.VisaoUnidadeRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Authenticated
@Path("/convenios-bancarios")
@Tag(name = "Convênio Bancário")
@PermissaoNecessaria({Acessos.CONVENIOS_BANCARIOS})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConvenioBancarioRest {

    @Inject
    ConvenioBancarioService convenioBancarioService;

    @Inject
    VisaoUnidadeRepository visaoUnidadeRepository;

    @Inject
    Mapper<ConvenioBancario, ConvenioBancarioDTO> responseMapper;

    @Inject
    Mapper<FaixaNossoNumero, FaixaNossoNumeroDTO> faixaNossoNumeroMapper;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @GET
    @Operation(summary = "Busca convênio bancário pelo id")
    @Path("/{idConvenioBancario}")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário não existe"),
    })
    public ConvenioBancarioDTO getById(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario
    ) {
        ConvenioBancario convenio = convenioBancarioService.getSeExistir(idConvenioBancario);
        return mapeiaCarregandoEntidades(convenio);
    }

    @GET
    @Operation(summary = "Busca convênio bancário pelo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário não existe"),
    })
    public PageResult<ConvenioBancarioDTO> getAllConveniosBancariosPaginado(
            @BeanParam ConvenioBancarioFitlerDTO convenioBancarioFitlerDTO
    ) {
        return convenioBancarioService.getAllConveniosBancariosPaginado(convenioBancarioFitlerDTO)
                .map(this::mapeiaCarregandoEntidades);
    }

    @GET
    @Operation(summary = "Busca convênio bancário ativo da unidade")
    @Path("/unidade/{idUnidade}")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Unidade não tem convênio bancário ativo"),
    })
    public ConvenioBancarioDTO getAtivoByUnidade(
            @Parameter(description = "Id da unidade", example = "3")
            @PathParam("idUnidade") @NotNull Integer idUnidade
    ) {
        return convenioBancarioService.getAtivoByUnidade(idUnidade)
                .map(this::mapeiaCarregandoEntidades)
                .orElseThrow(() -> new NaoEncontradoException("Nenhum Convênio ativo na unidade " + idUnidade));
    }

    @GET
    @Operation(summary = "Busca convênios bancários inativos da unidade")
    @Path("/unidade/inativos/{idUnidade}")
    public PageResult<ConvenioBancarioDTO> getInativosByUnidade(
            @Parameter(description = "Id da unidade", example = "3")
            @PathParam("idUnidade") @NotNull Integer idUnidade
    ) {
        return convenioBancarioService.getInativosByUnidade(idUnidade)
                .map(this::mapeiaCarregandoEntidades);
    }

    @PATCH
    @Operation(summary = "Desativar convênio bancário pelo id")
    @Path("/{idConvenioBancario}/desativar")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário não existe"),
    })
    public void desativar(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario,
            @Parameter(description = "Id do operador que realizou a alteração", example = "4")
            @QueryParam("idOperador") Integer idOperadorAlteracao
    ) {
        Integer idOperador = requestInfoHolder.getIdOperadorOu(idOperadorAlteracao);
        convenioBancarioService.desativarConvenio(idConvenioBancario, idOperador);
    }

    @PATCH
    @Operation(summary = "Ativar convênio bancário pelo id. Desativa outro convênio ativo da unidade se existir")
    @Path("/{idConvenioBancario}/ativar")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário não existe"),
    })
    public ConvenioBancarioDTO ativar(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario,
            @Parameter(description = "Id do operador que realizou a alteração", example = "4")
            @QueryParam("idOperador") Integer idOperadorAlteracao
    ) {
        Integer idOperador = requestInfoHolder.getIdOperadorOu(idOperadorAlteracao);

        return convenioBancarioService.ativarConvenio(idConvenioBancario, idOperador, this::mapeiaCarregandoEntidades);
    }

    @POST
    @Operation(summary = "Cria novo convênio bancário ativo. Desativa outro convênio ativo da unidade de existir")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Conta corrente não encontrada"),
    })
    @ResponseStatus(HttpStatus.SC_CREATED)
    public ConvenioBancarioDTO criarConvenioBancario(@RequestBody @Valid CriarConvenioBancarioDTO criarConvenioBancarioDTO) {
        requestInfoHolder.getIdOperador().ifPresent(idOperador -> {
            criarConvenioBancarioDTO.setIdOperadorInclusao(idOperador);
            criarConvenioBancarioDTO.getCriarFaixaNossoNumeroDTO().setIdOperadorInclusao(idOperador);
        });
        return convenioBancarioService.salvaNovoConvenioBancario(criarConvenioBancarioDTO, this::mapeiaCarregandoEntidades);
    }

    @POST
    @Path("/{idConvenioBancario}/faixas-nn")
    @Operation(summary = "Cria nova faixa de nosso número noconvênio bancário ativo. Desativa outras faixas se existir")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário ou faixa não existe"),
    })
    @ResponseStatus(HttpStatus.SC_CREATED)
    public FaixaNossoNumeroDTO criarFaixaConvenioBancario(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario,
            @RequestBody @Valid CriarFaixaNossoNumeroDTO criarFaixaNossoNumeroDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(criarFaixaNossoNumeroDTO::setIdOperadorInclusao);
        return convenioBancarioService.salvaNovaFaixaConvenioBancario(
                criarFaixaNossoNumeroDTO,
                idConvenioBancario,
                faixaNossoNumeroMapper::map
        );
    }

    @PUT
    @Path("/{idConvenioBancario}")
    @Operation(summary = "Altera convênio bancário")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio banário ou conta corrente não encontrada"),
    })
    public ConvenioBancarioDTO alterarConvenioBancario(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario,
            @RequestBody @Valid AlterarConvenioBancarioDTO alterarConvenioBancarioDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarConvenioBancarioDTO::setIdOperadorAlteracao);
        return convenioBancarioService.updateConvenioBancario(idConvenioBancario, alterarConvenioBancarioDTO, this::mapeiaCarregandoEntidades);
    }

    @DELETE
    @Path("/{idConvenioBancario}")
    @Operation(summary = "Exclui convênio bancário")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio banário ou conta corrente não encontrada"),
    })
    public void excluirConvenioBancario(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario
    ) {
        convenioBancarioService.excluirConvenioBancario(idConvenioBancario);
    }

    @PATCH
    @Operation(summary = "Ativar faixa do convênio bancário pelo id do convênio e da faixa. Desativa outra faixa ativo do convênio se existir")
    @Path("/{idConvenioBancario}/faixas-nn/{idFaixa}/ativar")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário ou faixa não existe"),
    })
    public List<FaixaNossoNumeroDTO> ativarFaixa(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario,
            @Parameter(description = "Id da faixa de nosso número", example = "23")
            @PathParam("idFaixa") @NotNull Integer idFaixa,
            @Parameter(description = "Id do operador que realizou a alteração", example = "4")
            @QueryParam("idOperador") Integer idOperadorAlteracao
    ) {
        Integer idOperador = requestInfoHolder.getIdOperadorOu(idOperadorAlteracao);
        return convenioBancarioService.ativarFaixaConvenio(idConvenioBancario, idFaixa, idOperador, faixaNossoNumeroMapper::map);
    }

    @PUT
    @Path("/{idConvenioBancario}/faixas-nn/{idFaixa}")
    @Operation(summary = "Altera faixa de nosso número de convênio bancário")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Convênio bancário ou faixa não existe"),
    })
    public FaixaNossoNumeroDTO alterarFaixaConvenioBancario(
            @Parameter(description = "Id do convênio bancário", example = "15")
            @PathParam("idConvenioBancario") @NotNull Integer idConvenioBancario,
            @Parameter(description = "Id da faixa de nosso número", example = "23")
            @PathParam("idFaixa") @NotNull Integer idFaixa,
            @RequestBody @Valid AlterarFaixaNossoNumeroDTO alterarFaixaNossoNumeroDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarFaixaNossoNumeroDTO::setIdOperadorAlteracao);
        return convenioBancarioService.updateFaixaConvenioBancario(
                idConvenioBancario,
                idFaixa,
                alterarFaixaNossoNumeroDTO,
                faixaNossoNumeroMapper::map
        );
    }

    private ConvenioBancarioDTO mapeiaCarregandoEntidades(ConvenioBancario convenioBancario) {
        if (convenioBancario.precisaCarregarUnidade()) {
            VisaoUnidade unidade = visaoUnidadeRepository.getByIdOrThrow(convenioBancario.getIdUnidade());
            convenioBancario.setUnidade(unidade);
        }

        return responseMapper.map(convenioBancario);
    }
}
