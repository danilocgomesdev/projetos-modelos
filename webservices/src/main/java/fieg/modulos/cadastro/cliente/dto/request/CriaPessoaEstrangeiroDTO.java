package fieg.modulos.cadastro.cliente.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CriaPessoaEstrangeiroDTO {

    private String descricao;
    private LocalDate dataNascimento;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone;
    private String telefone2;
    private LocalDateTime dataInclusao = LocalDateTime.now();
    private Integer idOperadorInclusao;
    private Boolean emancipado = false;
    private String celular;
    private String celular2;
    private String numeroResidencia;
    private String email;
    private String idEstrangeiro;
    private String pais;
    private Integer codigoPais;
    private String codigoPostal;
    private Integer idSistema = 40;
}
