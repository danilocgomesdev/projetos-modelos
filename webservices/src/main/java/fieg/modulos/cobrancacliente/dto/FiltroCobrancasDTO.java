package fieg.modulos.cobrancacliente.dto;

import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroIntegraProtheus;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroPagamento;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
public class FiltroCobrancasDTO {

    private FiltroPagamento filtroPagamento;
    private LocalDate dataVencimentoInicial;
    private LocalDate dataVencimentoFinal;
    private String sacadoNome;
    private String sacadoCpfCnpj;
    private Integer numeroParcela;
    private Integer contId;
    private Integer idSistema;
    private String nossoNumero;
    private String contratoProtheus;
    private Integer recno;
    private Boolean baixaIntegrada;
    private Integer idUnidade;
    private Integer idEntidade;
    private FormaPagamentoSimplificado formaPagamento;
    private FiltroIntegraProtheus filtroIntegraProtheus;

    // filtros do Protheus
    private Boolean saldoZeradoProtheus;
}
