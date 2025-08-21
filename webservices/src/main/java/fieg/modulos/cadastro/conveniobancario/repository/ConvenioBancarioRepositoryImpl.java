package fieg.modulos.cadastro.conveniobancario.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.conveniobancario.dto.ConvenioBancarioFitlerDTO;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;
import fieg.modulos.unidade.dto.subfiltros.FiltroEntidade;
import fieg.modulos.unidade.dto.subfiltros.FiltroUnidade;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class ConvenioBancarioRepositoryImpl implements ConvenioBancarioRepository, PanacheRepositoryBase<ConvenioBancario, Integer> {

    @Override
    public Optional<ConvenioBancario> getByIdOptional(Integer idConvenioBancario) {
        return findByIdOptional(idConvenioBancario);
    }

    @Override
    public Optional<ConvenioBancario> getAtivoByUnidade(Integer idUnidade) {
        return find("idUnidade = ?1 and dataInativo is null", idUnidade).firstResultOptional();
    }

    @Override
    public PageResult<ConvenioBancario> getInativosByUnidade(Integer idUnidade) {
        PanacheQuery<ConvenioBancario> query =  find("idUnidade = ?1 and dataInativo is not null", idUnidade);
        return PageResult.buildFromQuery(0, 20, query);
    }

    @Override
    public void persistConvenioBancario(ConvenioBancario convenioBancario) {
        persist(convenioBancario);
    }

    @Override
    public void deleteConvenioBancario(ConvenioBancario convenioBancario) {
        delete(convenioBancario);
    }

    @Override
    public PageResult<ConvenioBancario> getAllConveniosBancariosPaginado(ConvenioBancarioFitlerDTO convenioBancarioFitler) {
        var queryBuilder = new StringBuilder("1 = 1");
        var parameters = new Parameters();
        var sort = Sort.by("unidade.codigo").and("dataInativo", Sort.NullPrecedence.NULLS_FIRST);

        if (convenioBancarioFitler.getId() != null) {
            queryBuilder.append(" and id = :id");
            parameters.and("id", convenioBancarioFitler.getId());
        }

        if (UtilString.isNotBlank(convenioBancarioFitler.getNomeCedente())) {
            queryBuilder.append(" and nomeCedente like :nomeCedente");
            parameters.and("nomeCedente", "%" + convenioBancarioFitler.getNomeCedente() + "%");
        }

        if (UtilString.isNotBlank(convenioBancarioFitler.getNumero())) {
            queryBuilder.append(" and numero like :numero");
            parameters.and("numero", "%" + convenioBancarioFitler.getNumero() + "%");
        }

        if (UtilString.isNotBlank(convenioBancarioFitler.getNumeroConta())) {
            queryBuilder.append(" and concat(contaCorrente.numeroConta, '-', contaCorrente.digitoConta) like :numeroConta");
            parameters.and("numeroConta", "%" + convenioBancarioFitler.getNumeroConta() + "%");
        }

        if (UtilString.isNotBlank(convenioBancarioFitler.getNomeAgencia())) {
            queryBuilder.append(" and contaCorrente.agencia.nome like :nomeAgencia");
            parameters.and("nomeAgencia", "%" + convenioBancarioFitler.getNomeAgencia() + "%");
        }

        if (UtilString.isNotBlank(convenioBancarioFitler.getNomeBanco())) {
            queryBuilder.append(" and contaCorrente.agencia.banco.nome like :nomeBanco");
            parameters.and("nomeBanco", "%" + convenioBancarioFitler.getNomeBanco() + "%");
        }

        switch (convenioBancarioFitler.getFiltroUnidadeEntidade()) {
            case FiltroUnidade filtroUnidade -> {
                queryBuilder.append(" and idUnidade = :idUnidade");
                parameters.and("idUnidade", filtroUnidade.getIdUnidade());
            }
            case FiltroEntidade filtroEntidade -> {
                queryBuilder.append(" and unidade.entidade = :entidade");
                parameters.and("entidade", filtroEntidade.getEntidade());
            }
            case null -> {
            }
        }

        switch (convenioBancarioFitler.getStatus()) {
            case ATIVO -> queryBuilder.append(" and dataInativo is NULL");
            case INATIVO -> queryBuilder.append(" and dataInativo is not NULL");
            case null -> {
            }
        }

        PanacheQuery<ConvenioBancario> query = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(convenioBancarioFitler.getPage(), convenioBancarioFitler.getPageSize(), query);
    }
}
