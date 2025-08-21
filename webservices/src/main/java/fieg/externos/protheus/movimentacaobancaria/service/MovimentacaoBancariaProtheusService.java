package fieg.externos.protheus.movimentacaobancaria.service;

import fieg.externos.protheus.movimentacaobancaria.dto.MovimentacaoBancariaProtheusDTO;
import fieg.externos.protheus.movimentacaobancaria.enums.TipoMovimentacaoProt;

import java.util.List;

public interface MovimentacaoBancariaProtheusService {

    List<MovimentacaoBancariaProtheusDTO> getByContasAReceberRecno(
            Integer recnoContasAReceber,
            List<TipoMovimentacaoProt> tipos
    );
}
