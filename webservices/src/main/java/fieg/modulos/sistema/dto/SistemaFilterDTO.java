package fieg.modulos.sistema.dto;

import fieg.core.pagination.PageQuery;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;



@Getter
@Setter
public class SistemaFilterDTO extends PageQuery {


    @QueryParam("sistema")
    @Positive(message = "ID do Sistema precisa ser maior que zero")
    @DefaultValue("40")
    @Parameter(description = "ID do Sistema", required = true, schema = @Schema(type = SchemaType.INTEGER), example = "120")
    public Integer sistema;

    @QueryParam("descricao")
    @Parameter(description = "Descrição do Sistema", example = "CR5 - CONTROLE DE RECEBIMENTO 5 CASAS")
    public String descricao;

    @QueryParam("descricaoReduzida")
    @Parameter(description = "Descrição abreviada do Sistema", example = "CR5")
    public String descricaoReduzida;

}
