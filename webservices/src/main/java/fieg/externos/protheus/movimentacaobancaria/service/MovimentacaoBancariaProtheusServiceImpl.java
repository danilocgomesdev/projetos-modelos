package fieg.externos.protheus.movimentacaobancaria.service;

import fieg.externos.protheus.movimentacaobancaria.dto.MovimentacaoBancariaProtheusDTO;
import fieg.externos.protheus.movimentacaobancaria.enums.TipoMovimentacaoProt;
import fieg.externos.protheus.movimentacaobancaria.repository.MovimentacaoBancariaProtheusRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
class MovimentacaoBancariaProtheusServiceImpl implements MovimentacaoBancariaProtheusService {

    @Inject
    MovimentacaoBancariaProtheusRepository movimentacaoBancariaProtheusRepository;

    @Override
    public List<MovimentacaoBancariaProtheusDTO> getByContasAReceberRecno(
            Integer recnoContasAReceber,
            List<TipoMovimentacaoProt> tipos
    ) {
        return movimentacaoBancariaProtheusRepository.getByContasAReceberRecno(recnoContasAReceber, tipos);
    }
}
