package fieg.modulos.cadastro.contacorrente.dto;

import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;
import fieg.modulos.unidade.dto.UnidadeDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContaCorrenteDTO {

    private Integer id;
    private AgenciaDTO agencia;
    private String numeroOperacao;
    private String numeroConta;
    private String digitoConta;
    private Integer idUnidade;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;
    private String contaBanco;
    private String contaCliente;
    private String contaCaixa;
    private String contaJuros;
    private String contaDescontos;
    private String cofreBanco;
    private String cofreAgencia;
    private String cofreConta;

    private UnidadeDTO unidade;
}
