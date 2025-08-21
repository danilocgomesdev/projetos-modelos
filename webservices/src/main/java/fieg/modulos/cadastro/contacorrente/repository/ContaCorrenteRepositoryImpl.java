package fieg.modulos.cadastro.contacorrente.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteFilterDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.unidade.dto.subfiltros.FiltroEntidade;
import fieg.modulos.unidade.dto.subfiltros.FiltroUnidade;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
class ContaCorrenteRepositoryImpl implements ContaCorrenteRepository, PanacheRepositoryBase<ContaCorrente, Integer> {

    @Override
    public Optional<ContaCorrente> getByIdOptional(Integer idContaCorrente) {
        return findByIdOptional(idContaCorrente);
    }

    @Override
    public PageResult<ContaCorrente> getAllContaCorrentesPaginado(ContaCorrenteFilterDTO contaCorrenteFilter) {
        var queryBuilder = new StringBuilder("1 = 1");
        var parameters = new Parameters();
        var sort = Sort.by("unidade.codigo").and("agencia.banco.nome");

        if (contaCorrenteFilter.getId() != null) {
            queryBuilder.append(" and id = :id");
            parameters.and("id", contaCorrenteFilter.getId());
        }

        if (UtilString.isNotBlank(contaCorrenteFilter.getNumeroConta())) {
            queryBuilder.append(" and concat(numeroConta, '-', digitoConta) like :numeroConta");
            parameters.and("numeroConta", "%" + contaCorrenteFilter.getNumeroConta() + "%");
        }

        if (UtilString.isNotBlank(contaCorrenteFilter.getNomeAgencia())) {
            queryBuilder.append(" and agencia.nome like :nomeAgencia");
            parameters.and("nomeAgencia", "%" + contaCorrenteFilter.getNomeAgencia() + "%");
        }

        if (UtilString.isNotBlank(contaCorrenteFilter.getNomeBanco())) {
            queryBuilder.append(" and agencia.banco.nome like :nomeBanco");
            parameters.and("nomeBanco", "%" + contaCorrenteFilter.getNomeBanco() + "%");
        }

        switch (contaCorrenteFilter.getFiltroUnidadeEntidade()) {
            case FiltroUnidade filtroUnidade -> {
                queryBuilder.append(" and idUnidade = :idUnidade");
                parameters.and("idUnidade", filtroUnidade.getIdUnidade());
            }
            case FiltroEntidade filtroEntidade -> {
                queryBuilder.append(" and unidade.entidade = :entidade");
                parameters.and("entidade", filtroEntidade.getEntidade());
            }
            case null -> {}
        }

        PanacheQuery<ContaCorrente> contaCorrenteQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(contaCorrenteFilter.getPage(), contaCorrenteFilter.getPageSize(), contaCorrenteQuery);
    }

    @Override
    public void persistContaCorrente(ContaCorrente contaCorrente) {
        persist(contaCorrente);
    }

    @Override
    @Transactional
    public void deleteContaCorrente(ContaCorrente contaCorrente) {
        delete(contaCorrente);
    }
}
