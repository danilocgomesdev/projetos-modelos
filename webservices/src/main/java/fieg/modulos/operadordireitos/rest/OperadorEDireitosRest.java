package fieg.modulos.operadordireitos.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.operadordireitos.response.OperadorEDireitosDTO;
import fieg.modulos.operadordireitos.service.OperadorEDireitosService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/acessos")
@Tag(name = "Operador e Direitos")
// Qualquer um pode acessar
@PermissaoNecessaria
public class OperadorEDireitosRest {

    @Inject
    OperadorEDireitosService service;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @GET
    @Operation(summary = "Busca Operador e os acessos do Operador Logado. Funciona apenas pelo frontend")
    public OperadorEDireitosDTO getDireitosOperadorLogado() {
            return service.buscarPessoaEDireitos(requestInfoHolder.getIdPessoa().orElseThrow());
    }
}
