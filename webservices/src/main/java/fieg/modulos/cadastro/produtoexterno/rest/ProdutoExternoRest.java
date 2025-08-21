package fieg.modulos.cadastro.produtoexterno.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.produtoexterno.dto.AlterarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.CriarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoFilterDTO;
import fieg.modulos.cadastro.produtoexterno.model.ProdutoExterno;
import fieg.modulos.cadastro.produtoexterno.service.ProdutoExternoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

@Authenticated
@Path("/produto-externo")
@Tag(name = "Produtos")
@PermissaoNecessaria({Acessos.PRODUTO_CONTA_CONTABIL})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoExternoRest {

    @Inject
    ProdutoExternoService produtoExternoService;

    @Inject
    Mapper<ProdutoExterno, ProdutoExternoDTO> responseMapper;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @GET
    @Operation(summary = "Busca produto externo pelo id externo e sistema")
    public ProdutoExternoDTO getProdutoExternoByIdProdutoESistema(
            @QueryParam("idProduto") @NotNull Integer idProduto,
            @QueryParam("idSistema") @NotNull Integer idSistema
    ) {
        return produtoExternoService
                .buscarProdutoExternoIdSistemaId(idProduto, idSistema)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Produto Externo não encontrado"));
    }

    @GET
    @Path("/{idProdutoExterno}")
    @Operation(summary = "Busca produto externo pela sua chave primária")
    public ProdutoExternoDTO getProdutoExternoById(
            @PathParam("idProdutoExterno") @NotNull Integer idProdutoExterno
    ) {
        return produtoExternoService
                .getByIdOptional(idProdutoExterno)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Produto Externo não encontrado"));
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos os Produtos Externos paginado, filtrado por Id Sistema")
    public PageResult<ProdutoExternoDTO> getAllProdutosExternoPaginado(
            @BeanParam ProdutoExternoFilterDTO produtoExternoFilterDTO
    ) {
        return produtoExternoService.getAllProdutoExternoPaginado(produtoExternoFilterDTO).map(responseMapper::map);
    }

    @POST
    @Operation(summary = "Salva novo Produto Externo")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public ProdutoExternoDTO salvaNovoProdutoExterno(@RequestBody @Valid CriarProdutoExternoDTO produtoExternoDTO) {
        requestInfoHolder.getIdOperador().ifPresent(produtoExternoDTO::setIdOperadorInclusao);
        return produtoExternoService.salvaNovoProduto(produtoExternoDTO, responseMapper::map);
    }

    @PUT
    @Path("/{idProdutoExterno}")
    @Operation(summary = "Alterar Produto Externo")
    public ProdutoExternoDTO alterarProdutoExterno(
            @PathParam("idProdutoExterno") @NotNull Integer idProdutoExterno,
            @RequestBody @Valid AlterarProdutoExternoDTO produtoExternoDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(produtoExternoDTO::setIdOperadorAlteracao);
        return produtoExternoService.alteraProdutoExterno(idProdutoExterno, produtoExternoDTO, responseMapper::map);
    }

    @DELETE
    @Path("/{idProdutoExterno}")
    @Operation(summary = "Excluir Produto Externo pelo id externo e sistema")
    public void excluirProdutoExterno(
            @PathParam("idProdutoExterno") @NotNull Integer idProdutoExterno
    ) {
        produtoExternoService.excluirProdutoExterno(idProdutoExterno);
    }
}
