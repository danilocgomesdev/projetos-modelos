package fieg.modulos.cobrancacliente.dto.subfiltros;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FiltroDataPagamento extends FiltroPagamento {

    private final LocalDate dataPagamentoInicial;
    private final LocalDate dataPagamentoFinal;
}
