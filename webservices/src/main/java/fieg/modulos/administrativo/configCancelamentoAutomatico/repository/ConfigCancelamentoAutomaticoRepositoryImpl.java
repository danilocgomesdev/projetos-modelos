package fieg.modulos.administrativo.configCancelamentoAutomatico.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoFilterDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.model.ConfigCancelamentoAutomaticoContrato;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class ConfigCancelamentoAutomaticoRepositoryImpl implements ConfigCancelamentoAutomaticoRepository, PanacheRepositoryBase<ConfigCancelamentoAutomaticoContrato, Integer> {

    @Inject
    EntityManager em;

    @Override
    public PageResult<ConfigCancelamentoAutomaticoDTO> pesquisaConfiguracao(ConfigCancelamentoAutomaticoFilterDTO dto) {

        //language=SQL
        StringBuilder queryBuilder = new StringBuilder(
                """ 
                        SELECT CONSULTA.*, COUNT(*) OVER() AS TOTAL FROM
                            (SELECT CONFIG.ID_CFG_CANC_AUTOMATICO id, CONFIG.ID_SISTEMA idSistema, SISTEMA.DESCRICAO sistema, 
                            CONFIG.CFGCANC_CANCELAMENTO_AUTOMATICO cancelamentoAutomatico, 
                            CONVERT(VARCHAR(12),CAST(CONFIG.CFGCANC_DATA_ALTERACAO AS DATE)) dataAlteracao,
                            CONFIG.ID_OPERADOR_ALTERACAO idOperador, OPERADOR.NOME nomeOperador 
                            FROM CR5_CONFIG_CANCELAMENTO_AUTOMATICO_CONTRATO CONFIG
                            INNER JOIN CR5_VISAOSISTEMA SISTEMA ON SISTEMA.ID_SISTEMA = CONFIG.ID_SISTEMA
                            LEFT JOIN COMPARTILHADO..SF_OPERADOR OPERADOR ON OPERADOR.ID_OPERADOR = CONFIG.ID_OPERADOR_ALTERACAO
                            ) AS CONSULTA
                        """
        );

        Parameters parameters = new Parameters();
        queryBuilder.append(" WHERE 1=1  ");

        if (dto.getIdSistema() != null) {
            queryBuilder.append(" AND idSistema = :idSistema");
            parameters.and("idSistema", dto.getIdSistema());
        }

        queryBuilder.append(" ORDER BY idSistema  ");
        queryBuilder.append(dto.getStringPaginacao());

        Query query = em.createNativeQuery(queryBuilder.toString(), Tuple.class);
        parameters.map().forEach((key, value) -> query.setParameter(key, value));

        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);

        return pageResult
                .mapCollection(it -> it.stream()
                        .map(tuple -> new ConfigCancelamentoAutomaticoDTO(
                                tuple.get("id", Integer.class),
                                tuple.get("idSistema", Integer.class),
                                tuple.get("sistema", String.class),
                                tuple.get("cancelamentoAutomatico", Boolean.class),
                                tuple.get("dataAlteracao", String.class),
                                tuple.get("idOperador", Integer.class),
                                tuple.get("nomeOperador", String.class)
                        )).toList()
                );


    }

    @Override
    public Optional<ConfigCancelamentoAutomaticoContrato> getByIdOptional(Integer id) {
        return findByIdOptional(id);
    }

    public void persistConfig(ConfigCancelamentoAutomaticoContrato configCancelamentoAutomaticoContrato) {
        persist(configCancelamentoAutomaticoContrato);
    }
}
