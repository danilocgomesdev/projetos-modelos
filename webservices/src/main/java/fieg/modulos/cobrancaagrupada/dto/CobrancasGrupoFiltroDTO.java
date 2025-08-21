package fieg.modulos.cobrancaagrupada.dto;

import fieg.core.pagination.PageQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
public class CobrancasGrupoFiltroDTO extends PageQuery {

    private String nomeCliente;
    private String cpfCnpj;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Integer contIdInicial;
    private Integer contIdFinal;
    private String contProtheusInicial;
    private String contProtheusFinal;
    private Integer parcela;
    private String produto;
    private String nomeConsumidor;
    private String isContratoRede;
    private String nomePainel;
    private Integer idGrupo;
}
