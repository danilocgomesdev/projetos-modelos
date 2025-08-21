package fieg.modulos.conciliacao.conciliaexternos.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.model.Conciliacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConciliacaoService {

    Optional<Conciliacao> getByIdOptional(Integer idConciliacao);

    ConciliacaoDTO getConciliacaoPorContratoSistemaUnidade(Integer contId, Integer idSistema, Integer idUnidade);

    PageResult<ConciliacaoDTO> getAllConciliacaoPaginado(ConciliacaoFilterDTO conciliacaoFilterDTO);

    PageResult<ConciliacaoHitsDTO> getAllConciliacaoHitsPaginado(ConciliacaoHitsFilterDTO conciliacaoHitsFilterDTO);

    List<ConciliacaoHitsDTO> getAllConciliacaoHitsList(ConciliacaoHitsFilterDTO conciliacaoHitsFilterDTO);

    void prevalidarGerarReceberComParametro(LocalDate date);
}
