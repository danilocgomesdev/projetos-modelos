package fieg.modulos.cadastro.contacorrente.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.contacorrente.dto.AlterarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteFilterDTO;
import fieg.modulos.cadastro.contacorrente.dto.CriarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.cadastro.contacorrente.service.ContaCorrenteService;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import fieg.modulos.visao.visaounidade.repository.VisaoUnidadeRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

@Authenticated
@Path("/contas-corrente")
@Tag(name = "Contas Correntes")
@PermissaoNecessaria({Acessos.CONTAS_CORRENTES})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContaCorrenteRest {

    @Inject
    ContaCorrenteService contaCorrenteService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    VisaoUnidadeRepository visaoUnidadeRepository;

    @Inject
    Mapper<ContaCorrente, ContaCorrenteDTO> responseMapper;

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca conta corrente pelo id")
    public ContaCorrenteDTO getById(@PathParam("id") Integer idContaCorrente) {
        ContaCorrente contaCorrente = contaCorrenteService.getSeExistir(idContaCorrente);
        return mapeiaCarregandoEntidades(contaCorrente);
    }

    @GET
    @Operation(summary = "Busca todas as Contas Corrente conforme filtros")
    public PageResult<ContaCorrenteDTO> getAllContasCorrentepaginado(
            @BeanParam ContaCorrenteFilterDTO contaCorrenteFilter
    ) {
        return contaCorrenteService.getAllContaCorrentePaginado(contaCorrenteFilter)
                .map(this::mapeiaCarregandoEntidades);
    }

    @POST
    @Operation(summary = "Cria nova conta corrente")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public ContaCorrenteDTO criarContaCorrente(@Valid @RequestBody CriarContaCorrenteDTO criarContaCorrenteDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criarContaCorrenteDTO::setIdOperadorInclusao);
        return contaCorrenteService.criaContaCorrente(criarContaCorrenteDTO, this::mapeiaCarregandoEntidades);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Altera uma conta corrente existente")
    public ContaCorrenteDTO alterarContaCorrente(
            @PathParam("id") Integer idContaCorrente,
            @Valid @RequestBody AlterarContaCorrenteDTO alterarContaCorrenteDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarContaCorrenteDTO::setIdOperadorAlteracao);
        return contaCorrenteService.updateContaCorrente(idContaCorrente, alterarContaCorrenteDTO, this::mapeiaCarregandoEntidades);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Exclui uma conta corrente existente")
    public void excluiContaCorrente(@PathParam("id") Integer idContaCorrente) {
        contaCorrenteService.excluirContaCorrente(idContaCorrente);
    }
    
    private ContaCorrenteDTO mapeiaCarregandoEntidades(ContaCorrente cc) {
        if (cc.precisaCarregarUnidade()) {
            VisaoUnidade unidade = visaoUnidadeRepository.getByIdOrThrow(cc.getIdUnidade());
            cc.setUnidade(unidade);
        }

        return responseMapper.map(cc);
    }
}
