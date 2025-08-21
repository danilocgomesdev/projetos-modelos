package fieg.modulos.cadastro.agencias.dto;

import fieg.core.util.Mascaras;
import fieg.modulos.cadastro.bancos.dto.BancoDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgenciaDTO {

    private Integer id;
    private BancoDTO banco;
    private String cnpj;
    private String numero;
    private Character digitoVerificador;
    private String nome;
    private String cidade;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;

    public String getCnpj() {
        return Mascaras.formataCpfCnpj(cnpj);
    }
}
