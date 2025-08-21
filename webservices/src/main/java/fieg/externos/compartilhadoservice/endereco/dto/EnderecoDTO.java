package fieg.externos.compartilhadoservice.endereco.dto;

import lombok.Data;

@Data
public class EnderecoDTO {

    private String cep;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String uf;
    private String complemento;
    private String tipoLogradouro;
    private String codigoEstado;
    private String codigoMunicipio;
    private Integer codigoIBGE;
}
