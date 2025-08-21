package fieg.modulos.administrativo.pagamentosnaobaixados.dto;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FiltroBuscaPagamentoNaoBaixadoDTO {

    @QueryParam("idUnidade")
    private Integer idUnidade;

    @QueryParam("valorMaximo")
    private BigDecimal valorMaximo;

    @QueryParam("dataInicial")
    private LocalDate dataPagamentoInicial;

    @QueryParam("dataFinal")
    private LocalDate dataPagamentoFinal;
}
