package fieg.externos.compartilhadoservice.unidade;

import fieg.modulos.entidade.dto.EntidadeDTO;
import lombok.Data;

@Data
public class UnidadeCIResponseDTO {
    private Integer id;
    private Integer ano;
    private String codigo;
    private String nome;
    private EntidadeDTO entidade;
    private String filialERP;
    private String centroCustoErp;
    private Integer idLocal;
    private String cidade;
    private String uf;
}

