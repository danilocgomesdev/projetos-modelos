package fieg.modulos.unidade.dto;

import fieg.modulos.entidade.enums.Entidade;
import lombok.Data;

@Data
public class UnidadeDTO {
    private Integer id;
    private Integer ano;
    private String codigo;
    private String nome;
    private String descricaoUnidade;
    private Entidade entidade;
    private String filialERP;
    private String centroCustoErp;
    private Integer idLocal;
    private String cidade;
    private String uf;
}
