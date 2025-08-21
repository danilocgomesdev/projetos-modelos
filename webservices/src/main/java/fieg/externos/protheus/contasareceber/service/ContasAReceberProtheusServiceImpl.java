package fieg.externos.protheus.contasareceber.service;

import fieg.externos.protheus.contasareceber.dto.ContasAReceberProtheusDTO;
import fieg.externos.protheus.contasareceber.repository.ContasAReceberProtheusRepository;
import fieg.externos.protheus.movimentacaobancaria.dto.MovimentacaoBancariaProtheusDTO;
import fieg.externos.protheus.movimentacaobancaria.enums.TipoMovimentacaoProt;
import fieg.externos.protheus.movimentacaobancaria.service.MovimentacaoBancariaProtheusService;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class ContasAReceberProtheusServiceImpl implements ContasAReceberProtheusService {

    @Inject
    ContasAReceberProtheusRepository contasAReceberProtheusRepository;

    @Inject
    MovimentacaoBancariaProtheusService movimentacaoBancariaProtheusService;

    @Override
    public Optional<ContasAReceberProtheusDTO> getByCobrancaCliente(CobrancaCliente cobrancaCliente) {
        Optional<ContasAReceberProtheusDTO> maybeMov = contasAReceberProtheusRepository.getByCobrancaCliente(cobrancaCliente);

        maybeMov.ifPresent(movimentacao -> {
            List<MovimentacaoBancariaProtheusDTO> baixas = movimentacaoBancariaProtheusService.getByContasAReceberRecno(
                    movimentacao.getRecno(),
                    List.of(
                            TipoMovimentacaoProt.VALOR,
                            TipoMovimentacaoProt.BAIXA_AUTOMATICA
                    )
            );
            movimentacao.setBaixas(baixas);
        });

        return maybeMov;
    }

    @Override
    public Optional<Integer> getRecno(CobrancaCliente cobrancaCliente) {
        return contasAReceberProtheusRepository.getRecno(cobrancaCliente);
    }
}
