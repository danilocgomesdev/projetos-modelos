package fieg.modulos.cadastro.cliente.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PessoaCr5DTO {

    private Integer idPessoa;
    private Integer idSistema;
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
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;
    private Integer idResponsavelFinanceiro;
    private Boolean pessoaFisica;
    private String inscricaoEstadual;
    private Boolean emancipado;
    private String celular;
    private String celular2;
    private String numeroResidencia;
    private String email;
    private String idEstrangeiro;
    private String pais;
    private Boolean estrangeiro;
    private Integer codigoPais;
    private String codigoPostal;

}
