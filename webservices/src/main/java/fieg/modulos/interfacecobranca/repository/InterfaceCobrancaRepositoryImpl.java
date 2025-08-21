package fieg.modulos.interfacecobranca.repository;

import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class InterfaceCobrancaRepositoryImpl implements InterfaceCobrancaRepository, PanacheRepositoryBase<InterfaceCobranca, Integer> {

    @Inject
    Logger logger;

    @Override
    public Optional<InterfaceCobranca> findByIdOptional(Integer idInterface) {
        return find("id", idInterface).firstResultOptional();
    }

    @Override
    public List<InterfaceCobranca> selectInterfacesByIds(List interfacesCobrancas) {
        List<InterfaceCobranca> interfaces = find("id in (?1)", interfacesCobrancas).list();

        interfaces.forEach(i -> {
            Hibernate.initialize(i.getProtheusContrato());
            i = getEntityManager().merge(i);
        });

        return interfaces;
    }

    @Override
    public InterfaceCobranca findByIdCobrancaCliente(Integer idCobrancaCliente) {
        return find("SELECT i FROM InterfaceCobranca i " +
                "JOIN i.cobrancasCliente c " +
                "WHERE c.id = ?1", idCobrancaCliente)
                .firstResult();
    }

    @Override
    public void atualizarInterfaceCobranca(InterfaceCobranca interfaceCobranca) {
        getEntityManager().merge(interfaceCobranca);
    }

}
