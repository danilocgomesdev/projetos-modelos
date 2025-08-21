package fieg.modulos.cadastro.impressora.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class ImpressoraFilterDTO extends PageQuery {


    @QueryParam("idImpressora")
    @Parameter(description = "Id da Impressora", example = "001")
    private Integer idImpressora;

    @QueryParam("descricao")
    @Parameter(description = "Descrição da Impressora", example = "IMPRESSORA BALCÃO")
    private String descricao;

    @QueryParam("modelo")
    @Parameter(description = "Moledo da Impressora", example = "TÉRMICA")
    private String modelo;

    @QueryParam("ipMaquina")
    @Parameter(description = "IP máquina onde está a Impressora", example = "192.168.0.1")
    private String ipMaquina;

    @QueryParam("idUnidade")
    @Parameter(description = "Id da Unidade", example = "001")
    private Integer idUnidade;

}
