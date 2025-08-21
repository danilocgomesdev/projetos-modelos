package fieg.externos.protheus.produtoservico.rest;

import fieg.externos.protheus.dto.AlterarProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.IncluirProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.RespostaProtheusDTO;
import fieg.externos.protheus.produtoservico.service.ProdutoServicoProtheusService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("protheus/produtoservico")
@Tag(name = "Produto/Serviço Protheus")
public class ProdutoServicoProtheusRest {

    @Inject
    ProdutoServicoProtheusService produtoServicoProtheusService;

    @POST
    @Operation(summary = "Incluir Produto / Serviço no Protheus")
    public RespostaProtheusDTO incluirProdutoNoProtheus(IncluirProdutoNoProtheusDTO dto) {
        return produtoServicoProtheusService.incluirProdutoNoProtheus(dto);
    }

    @PUT
    @Operation(summary = "Alterar Produto / Serviço no Protheus")
    public RespostaProtheusDTO alterarProdutoNoProtheus(AlterarProdutoNoProtheusDTO dto) {
        return produtoServicoProtheusService.alterarProdutoNoProtheus(dto);
    }


}
