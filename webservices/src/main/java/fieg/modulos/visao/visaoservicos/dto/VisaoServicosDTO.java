package fieg.modulos.visao.visaoservicos.dto;

import lombok.Data;

@Data
public class VisaoServicosDTO {

    private Integer idProduto;
    private Integer idSistema;
    private String nome;
    private Character status;
    private String codProdutoProtheus;

}