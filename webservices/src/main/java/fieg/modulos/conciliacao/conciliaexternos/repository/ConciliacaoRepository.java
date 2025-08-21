package fieg.modulos.conciliacao.conciliaexternos.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.model.Conciliacao;

import java.util.List;
import java.util.Optional;

public interface ConciliacaoRepository {

    Optional<Conciliacao> getByIdOptional(Integer idConciliacao);

    Optional<Conciliacao> getConciliacaoPorContratoSistemaUnidade(Integer contId, Integer idSistema, Integer idUnidade);

    PageResult<Conciliacao> getAllConciliacaoPaginado(ConciliacaoFilterDTO conciliacaoFilterDTO);

    void persistConciliacao(Conciliacao conciliacao);

    void deleteConciliacao(Conciliacao conciliacao);

    PageResult<ConciliacaoHitsDTO> getConciliacaoHitsPaginado(ConciliacaoHitsFilterDTO dto);

    List<ConciliacaoHitsDTO> getConciliacaoHitsList(ConciliacaoHitsFilterDTO dto);
}
