package fieg.modulos.cadastro.cliente.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriaPessoaJuridicaDTO {

    private String razaoSocial;
    private String cnpj;
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
    private LocalDateTime dataAlteracaoProtheus;
    private LocalDateTime dataAlteracaoProt;
    private Character statusProt;
    private String inscricaoEstadual;
    private String celular;
    private String celular2;
    private String numeroResidencia;
    private String email;
    private Integer idSistema = 40;
    private Boolean emancipado = false;

}
