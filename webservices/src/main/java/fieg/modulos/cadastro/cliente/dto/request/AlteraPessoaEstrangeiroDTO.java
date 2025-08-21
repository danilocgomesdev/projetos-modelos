package fieg.modulos.cadastro.cliente.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AlteraPessoaEstrangeiroDTO {

    private Integer idPessoa;
    private String descricao;
    private LocalDate dataNascimento;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String numeroResidencia;
    private String cidade;
    private String estado;
    private String pais;
    private Integer codigoPais;
    private String codigoPostal;
    private String telefone;
    private String telefone2;
    private LocalDateTime dataAlteracao = LocalDateTime.now();
    private Integer idOperadorAlteracao;
    private Boolean emancipado = false;
    private String celular;
    private String celular2;
    private String email;
    private String idEstrangeiro;
    private Integer idSistema = 40;
}
