package fieg.modulos.contratogestor.repository;

import fieg.modulos.contratogestor.model.ContratoGestor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;


@ApplicationScoped
class ContratoGestorRepositoryImpl implements ContratoGestorRepository, PanacheRepositoryBase<ContratoGestor, Integer> {


    @Override
    public void salvar(ContratoGestor contratoGestor) {
        getEntityManager().merge(contratoGestor);
    }

    @Override
    public boolean verificarSeContratoRede(Integer contId, Integer sistemaId) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append(" select distinct cg from ContratoGestor cg \n");
            hql.append(" join cg.unidadeGestora u \n");
            hql.append(" join cg.responsavel p \n");
            hql.append(" join cg.interfacesCobrancas i  \n");
            hql.append(" join i.cobrancasClientes cc \n");
            hql.append(" where i.contId = :contId and i.sistemaId = :sistemaId and u.ano = year(GETDATE()) \n");

            Query q = getEntityManager().createQuery(hql.toString(), ContratoGestor.class);
            q.setParameter("contId", contId);
            q.setParameter("sistemaId", sistemaId);

            ContratoGestor contratoGestor = (ContratoGestor) q.getSingleResult();
            return contratoGestor != null;
        } catch (NoResultException e) {
            return false;
        }
    }
}
