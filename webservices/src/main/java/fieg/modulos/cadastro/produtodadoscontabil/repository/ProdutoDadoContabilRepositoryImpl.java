package fieg.modulos.cadastro.produtodadoscontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.produtodadoscontabil.dto.ProdutoDadoContabilFilterDTO;
import fieg.modulos.cadastro.produtodadoscontabil.model.ProdutoDadoContabil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class ProdutoDadoContabilRepositoryImpl implements ProdutoDadoContabilRepository, PanacheRepositoryBase<ProdutoDadoContabil, Integer> {

    @Override
    public Optional<ProdutoDadoContabil> getProdutoDadoContabilById(Integer idProdutoDadosContabil) {
        return findByIdOptional(idProdutoDadosContabil);
    }

    @Override
    public Optional<ProdutoDadoContabil> getProdutoDadoContabilByIdProdutoIdSistema(Integer idProduto, Integer idSistema) {
        return find("idProduto = ?1 and idSistema = ?2", idProduto, idSistema).firstResultOptional();
    }

    @Override
    public PageResult<ProdutoDadoContabil> getAllProdutoDadoContabilPaginado(ProdutoDadoContabilFilterDTO dto) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("idProdutoDadoContabil");

        if (dto.getIdProdutoDadoContabil() != null) {
            queryBuilder.append(" and idProdutoDadoContabil = :idProdutoDadoContabil");
            parameters.and("idProdutoDadoContabil", dto.getIdProdutoDadoContabil());
        }
        if (dto.getIdDadoContabil() != null) {
            queryBuilder.append(" and idDadoContabil = :idDadoContabil");
            parameters.and("idDadoContabil", dto.getIdDadoContabil());
        }
        if (dto.getIdProduto() != null) {
            queryBuilder.append(" and idProduto = :idProduto");
            parameters.and("idProduto", dto.getIdProduto());
        }
        if (dto.getProduto() != null) {
            queryBuilder.append(" and produto = :produto");
            parameters.and("produto", dto.getProduto());
        }
        if (dto.getStatus() != null) {
            queryBuilder.append(" and status = :status");
            parameters.and("status", dto.getStatus());
        }
        if (dto.getCodProdutoProtheus() != null) {
            queryBuilder.append(" and codProdutoProtheus = :codProdutoProtheus");
            parameters.and("codProdutoProtheus", dto.getCodProdutoProtheus());
        }
        if (dto.getPreco() != null) {
            queryBuilder.append(" and preco = :preco");
            parameters.and("preco", dto.getPreco());
        }
        if (dto.getIdSistema() != null) {
            queryBuilder.append(" and idSistema = :idSistema");
            parameters.and("idSistema", dto.getIdSistema());
        }
        if (UtilString.isNotBlank(dto.getDmed())) {
            queryBuilder.append(" and dmed like :dmed");
            parameters.and("dmed", "%" + dto.getDmed() + "%");
        }
        if (dto.getIdOperadorInclusao() != null) {
            queryBuilder.append(" and idOperadorInclusao = :idOperadorInclusao");
            parameters.and("idOperadorInclusao", dto.getIdOperadorInclusao());
        }
        if (dto.getDataInclusao() != null) {
            queryBuilder.append(" and dataInclusao = :dataInclusao");
            parameters.and("dataInclusao", dto.getDataInclusao());
        }
        if (dto.getIdOperadorAlteracao() != null) {
            queryBuilder.append(" and idOperadorAlteracao = :idOperadorAlteracao");
            parameters.and("idOperadorAlteracao", dto.getIdOperadorAlteracao());
        }
        if (dto.getDataAlteracao() != null) {
            queryBuilder.append(" and dataAlteracao = :dataAlteracao");
            parameters.and("dataAlteracao", dto.getDataAlteracao());
        }
        if (dto.getDataInativacao() != null) {
            queryBuilder.append(" and dataInativacao = :dataInativacao");
            parameters.and("dataInativacao", dto.getDataInativacao());
        }

        PanacheQuery<ProdutoDadoContabil> produtoDadosContabilPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(dto.getPage(), dto.getPageSize(), produtoDadosContabilPanacheQuery);
    }

    @Override
    public void salvarProdutoDadoContabil(ProdutoDadoContabil produtoDadoContabil) {
        persist(produtoDadoContabil);
    }


}