package fieg.modulos.administrativo.cartao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TerminalTefDTO {

    private String id;
    private String unidCodigo;
    private String unidDescricao;
    private String empresaDescricao;
    private Integer entidadeIdLocal;
    private Integer codTerminal;
    private String smpDtAtualizacao;
    private BigDecimal smpVersao;
    private String hostIp;
    private String hostName;


}
