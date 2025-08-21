package fieg.modulos.conciliacao.conciliaexternos.service;


import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.externos.pagamentoexterno.PagamentoExternoRestClient;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.mapper.ConciliacaoMapper;
import fieg.modulos.conciliacao.conciliaexternos.model.Conciliacao;
import fieg.modulos.conciliacao.conciliaexternos.repository.ConciliacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
class ConciliacaoServiceImpl implements ConciliacaoService {

    @Inject
    ConciliacaoRepository conciliacaoRepository;

    @Inject
    ConciliacaoMapper conciliacaoMapper;

    @Inject
    @RestClient
    PagamentoExternoRestClient pagamentoExternoRestClient;


    @Override
    public Optional<Conciliacao> getByIdOptional(Integer idConciliacao) {
        return conciliacaoRepository.getByIdOptional(idConciliacao);
    }

    @Override
    public ConciliacaoDTO getConciliacaoPorContratoSistemaUnidade(Integer contId, Integer idSistema, Integer idUnidade) {
        return conciliacaoRepository.getConciliacaoPorContratoSistemaUnidade(contId, idSistema, idUnidade).map(conciliacaoMapper::toDto)
                .orElseThrow(() -> new NaoEncontradoException("Conciliação não encontrada"));
    }

    @Override
    public PageResult<ConciliacaoDTO> getAllConciliacaoPaginado(ConciliacaoFilterDTO conciliacaoFilterDTO) {
        return conciliacaoRepository.getAllConciliacaoPaginado(conciliacaoFilterDTO).map(conciliacaoMapper::toDto);
    }

    @Override
    public PageResult<ConciliacaoHitsDTO> getAllConciliacaoHitsPaginado(ConciliacaoHitsFilterDTO conciliacaoHitsFilterDTO) {
        return conciliacaoRepository.getConciliacaoHitsPaginado(conciliacaoHitsFilterDTO);
    }

    @Override
    public List<ConciliacaoHitsDTO> getAllConciliacaoHitsList(ConciliacaoHitsFilterDTO conciliacaoHitsFilterDTO) {
        return conciliacaoRepository.getConciliacaoHitsList(conciliacaoHitsFilterDTO);
    }

    @Override
    public void prevalidarGerarReceberComParametro(LocalDate date) {
        pagamentoExternoRestClient.prevalidarGerarReceberComParametro(date);
    }

}
