package fieg.modulos.cadastro.cliente.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CriaPessoaCr5DTO {

    private Integer idTabela;
    private Integer idSistema = 40;
    private String descricao;
    private String cpfCnpj;
    private String rg;
    private LocalDate dataNascimento;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String telefone2;
    private LocalDateTime dataInclusao = LocalDateTime.now();
    private Integer idOperadorInclusao;
    private Integer idRespFin;
    private Boolean pessoaFisica;
    private String filialProtheus;
    private String codClienteProtheus;
    private String lojaProtheus;
    private Character statusProtheus = 'L';
    private String inscricaoEstadual;
    private Boolean emancipado;
    private String celular;
    private String celular2;
    private String numeroResidencia;
    private String email;
    private Boolean estrangeiro;
    private String pais = "BRASIL";
    private String idEstrangeiro;
    private Integer codigoPais = 105;
    private String codigoPostal;

}
