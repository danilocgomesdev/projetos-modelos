package fieg.externos.protheus.movimentacaobancaria.repository;

import fieg.externos.protheus.movimentacaobancaria.dto.MovimentacaoBancariaProtheusDTO;
import fieg.externos.protheus.movimentacaobancaria.enums.TipoMovimentacaoProt;

import java.util.List;

public interface MovimentacaoBancariaProtheusRepository {

    List<MovimentacaoBancariaProtheusDTO> getByContasAReceberRecno(
            Integer recnoContasAReceber,
            List<TipoMovimentacaoProt> tipos
    );
}
