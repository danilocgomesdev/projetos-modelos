package fieg.modulos.cadastro.dadocontabil.dto;

import fieg.core.enums.EnumAtivoInativo;
import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class DadoContabilFilterDTO extends PageQuery {

    @QueryParam("idDadoContabil")
    @Parameter(description = "Id do Dado Contábil", example = "1")
    private Integer idDadoContabil;

    @QueryParam("contaContabil")
    @Parameter(description = "Número da Conta Contábil", example = "41010406")
    private String contaContabil;

    @QueryParam("contaContabilDescricao")
    @Parameter(description = "Descrição da Conta Contábil", example = "SERVIÇOS EDUCACIONAIS")
    private String contaContabilDescricao;

    @QueryParam("itemContabil")
    @Parameter(description = "Item Contábil", example = "101")
    private String itemContabil;

    @QueryParam("itemContabilDescricao")
    @Parameter(description = "Descrição do Item Contábil", example = "TAXAS")
    private String itemContabilDescricao;

    @QueryParam("natureza")
    @Parameter(description = "Código da Natureza", example = "1010160022")
    private String natureza;

    @QueryParam("naturezaDescricao")
    @Parameter(description = "Descrição da Natureza", example = "EDUCAÇÃO CONTINUADA")
    private String naturezaDescricao;

    @QueryParam("idEntidade")
    @Parameter(description = "Id da Entidade", example = "99")
    private Integer idEntidade;

    @QueryParam("idOperadorInclusao")
    @Parameter(description = "Id do Operador de Inclusão", example = "20")
    private Integer idOperadorInclusao;

    @QueryParam("idOperadorAlteracao")
    @Parameter(description = "Id do Operador de Alteração", example = "30")
    private Integer idOperadorAlteracao;

    @QueryParam("dataInclusao")
    @Parameter(description = "Data de Inclusão", example = "2023-01-01T10:00:00")
    private LocalDateTime dataInclusao;

    @QueryParam("dataAlteracao")
    @Parameter(description = "Data de Alteração", example = "2023-06-01T15:00:00")
    private LocalDateTime dataAlteracao;

    @QueryParam("status")
    @Parameter(description = "Status do Produto", example = "A ou I")
    private EnumAtivoInativo status;


}
