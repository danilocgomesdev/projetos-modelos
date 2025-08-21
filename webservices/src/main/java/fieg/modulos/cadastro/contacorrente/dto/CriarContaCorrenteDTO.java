package fieg.modulos.cadastro.contacorrente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarContaCorrenteDTO {

    @NotNull
    private Integer idAgencia;

    @NotBlank
    private String numeroOperacao;

    @NotBlank
    private String numeroConta;

    private String digitoConta;

    @NotNull
    private Integer idUnidade;

    private Integer idOperadorInclusao;

    private String contaBanco;

    private String contaCliente;

    private String contaCaixa;

    private String contaJuros;

    private String contaDescontos;

    private String cofreBanco;

    private String cofreAgencia;

    private String cofreConta;

    private LocalDateTime dataInclusao = LocalDateTime.now();
}
