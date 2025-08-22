package fieg.modulos.cr5.recorrencia.repository;


import fieg.modulos.cr5.recorrencia.dto.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import fieg.core.util.RefletionUtil;

@SuppressWarnings("unchecked")
@ApplicationScoped
public class RecorrenciaRepository {

    private final EntityManager em;

    @Inject
    public RecorrenciaRepository(EntityManager em) {
        this.em = em;
    }

    public List<RecorrenciaCR5DTO> getRecorrenciasAtiva() {


        List<Object[]> list = new ArrayList<>();


        String sql = """
                       SELECT DISTINCT PR.CR5_ID_PAGAMENTO_RECORRENTE id, convert(integer, E.ENTIDADE) entidade, \s
                       PR.CR5_RECURRENT_PAYMENT_ID idRecorrencia, PR.CR5_RECURRENT_STATUS statusRecorrencia, 
                       PR.CR5_START_DATE inicioData, 
                       PR.CR5_END_DATE fimData \s
                       FROM CR5_PAGAMENTO_RECORRENTE PR
                       INNER JOIN CR5_PEDIDO PED  ON PR.PED_CODIGO_COMPOSTO  = PED.PED_CODIGO_COMPOSTO\s
                       INNER JOIN CR5_FORMASPAGTO F ON F.ID_PEDIDO = PED.ID_PEDIDO
                       INNER JOIN CR5_COBRANCAS_CLIENTES C ON F.ID_COBRANCAS_PAGTO = C.ID_COBRANCAS_PAGTO
                       INNER JOIN CR5_PESSOAS  P ON P.ID_PESSOAS = C.ID_PESSOAS
                       INNER join CR5_VisaoUnidade u on u.ID_UNIDADE = C.ID_UNIDADE
                       INNER join CR5_VisaoEntidade e on u.ENTIDADE = e.ENTIDADE
                       where PR.CR5_RECURRENT_STATUS = 'Ativa' 
                       and convert(integer, E.ENTIDADE) = '2'
                       ORDER BY PR.CR5_ID_PAGAMENTO_RECORRENTE
                   
                       """;



       final Query query = em.createNativeQuery(sql, Tuple.class);

       final List<Tuple> tuples = query.getResultList();

       return RefletionUtil.mapTuplesToObjects(tuples, RecorrenciaCR5DTO.class);


    }

    @Transactional
    public Integer cancelarRecorrencia(String idRecorrencia) {
        //language=sql
        Integer sucesso;
        String sql = """
                UPDATE CR5_PAGAMENTO_RECORRENTE SET CR5_RECURRENT_STATUS = 'Cancelada', DATA_CANCELAMENTO = GETDATE()
                WHERE CR5_RECURRENT_PAYMENT_ID = :idRecorrencia
                """;
        Query query = em.createNativeQuery(sql);
        query.setParameter("idRecorrencia", idRecorrencia);
        sucesso = query.executeUpdate();


        return sucesso ;
    }
}
