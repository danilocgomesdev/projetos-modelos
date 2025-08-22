
package fieg.modulos.cr5.cielo;


import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cieloJobs.enums.EnumSituacaoProblema;
import fieg.modulos.cieloJobs.enums.TipoErroConciliacao;
import fieg.modulos.cr5.model.InconsistenciaConciliacaoCielo;
import fieg.modulos.cr5.model.TransacaoTefParc;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class InconsistenciaVendaCieloRS {

    public void salvarInconsistencia(TransacaoCieloDTO dto, TipoErroConciliacao tipoErroConciliacao) {
        salvarInconsistencia(dto, tipoErroConciliacao, null);
    }

    @Transactional
    public void salvarInconsistencia(TransacaoCieloDTO dto, TipoErroConciliacao tipoErro, TransacaoTefParc parcela) {
        Log.infof("Salvando Inconsistencia de conciliacao para a transacao: %s, tipo: %s", dto.stringIdentificacao(), tipoErro);
        try {
            InconsistenciaConciliacaoCielo inconsistenciaExistente = buscaInconsistencia(dto, tipoErro).orElse(null);

            if (inconsistenciaExistente != null) {
                if (inconsistenciaExistente.getSituacaoProblema() == EnumSituacaoProblema.IGNORADO) {
                    return;
                }

                Log.infof("Salvando Inconsistencia %s para NAO_RESOLVIDO", inconsistenciaExistente);
                inconsistenciaExistente.setSituacaoProblema(EnumSituacaoProblema.NAO_RESOLVIDO);
                InconsistenciaConciliacaoCielo.persist(inconsistenciaExistente);
            } else {
                InconsistenciaConciliacaoCielo inconsistencia = new InconsistenciaConciliacaoCielo(dto, tipoErro, parcela);
                Log.infof("Salvando Inconsistencia %s", inconsistencia);
                InconsistenciaConciliacaoCielo.persist(inconsistencia);
            }
        } catch (Exception e) {
            Log.error("Erro ao salvar Inconsitencia Conciliacao", e);
        }
    }

    @Transactional
    public void resolveInconsistencias(TransacaoCieloDTO dto) {
        try {
            for (var inconsistenciaExistente : buscaInconsistenciasNaoResolvidas(dto)) {
                Log.infof("Salvando Inconsistencia %s para RESOLVIDO_AUTOMATICAMENTE", inconsistenciaExistente);
                inconsistenciaExistente.setSituacaoProblema(EnumSituacaoProblema.RESOLVIDO_AUTOMATICAMENTE);
                InconsistenciaConciliacaoCielo.persist(inconsistenciaExistente);
            }
        } catch (Exception e) {
            Log.error("Erro ao resolver Inconsitencia Conciliacao", e);
        }
    }

    public Optional<InconsistenciaConciliacaoCielo> buscaInconsistencia(TransacaoCieloDTO dto, TipoErroConciliacao tipoErro) {
        return InconsistenciaConciliacaoCielo.find(
                """
                        tid = ?1
                        and codigoAutorizacao = ?2
                        and numeroNsu = ?3
                        and estabelecimentoSubmissor = ?4
                        and dataMovimento = ?5
                        and isPagamento = ?6
                        and tipoErroConciliacao = ?7
                        """,
                dto.getTid(),
                dto.getCodigoAutorizacao(),
                dto.getNumeroNSU(),
                dto.getEstabelecimentoSubmissor(),
                dto.getDataMovimento(),
                dto.getArquivoPagamento(),
                tipoErro
        ).firstResultOptional();
    }

    public List<InconsistenciaConciliacaoCielo> buscaInconsistenciasNaoResolvidas(TransacaoCieloDTO dto) {
        return InconsistenciaConciliacaoCielo.find(
                """
                        tid = ?1
                        and codigoAutorizacao = ?2
                        and numeroNsu = ?3
                        and estabelecimentoSubmissor = ?4
                        and dataMovimento = ?5
                        and isPagamento = ?6
                        and situacaoProblema not in ?7
                        """,
                dto.getTid(),
                dto.getCodigoAutorizacao(),
                dto.getNumeroNSU(),
                dto.getEstabelecimentoSubmissor(),
                dto.getDataMovimento(),
                dto.getArquivoPagamento(),
                List.of(EnumSituacaoProblema.RESOLVIDO, EnumSituacaoProblema.RESOLVIDO_AUTOMATICAMENTE)
        ).list();
    }
}
