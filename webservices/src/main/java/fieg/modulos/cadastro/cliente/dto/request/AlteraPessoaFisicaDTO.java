package fieg.modulos.cadastro.cliente.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AlteraPessoaFisicaDTO {

    private Integer idPessoa;
    private String cpf;
    private String rg;
    private String descricao;
    private LocalDate dataNascimento;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String telefone2;
    private LocalDateTime dataAlteracao = LocalDateTime.now();
    private Integer idOperadorAlteracao;
    private Integer idResponsavelFinanceiro;
    private Boolean emancipado = false;
    private Boolean estrangeiro = false;
    private String celular;
    private String celular2;
    private String numeroResidencia;
    private String email;
    private Integer idSistema = 40;

}
