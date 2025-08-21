package fieg.modulos.cadastro.produtoexterno.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoFilterDTO;
import fieg.modulos.cadastro.produtoexterno.model.ProdutoExterno;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class ProdutoExternoRepositoryImpl implements ProdutoExternoRepository, PanacheRepositoryBase<ProdutoExterno, Integer> {

    @Override
    public Optional<ProdutoExterno> buscarProdutoExternoIdSistemaId(Integer idProduto, Integer idSistema) {
        return find("idProduto = ?1 and idSistema = ?2", idProduto, idSistema).firstResultOptional();
    }

    @Override
    public Optional<ProdutoExterno> getByIdOptional(Integer idProdutoExterno) {
        return find("id", idProdutoExterno).firstResultOptional();
    }

    @Override
    public PageResult<ProdutoExterno> getAllProdutoExternoPaginado(ProdutoExternoFilterDTO produtoExternoFilterDTO) {
        StringBuilder query = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("id");

        if (produtoExternoFilterDTO.getIdProduto() != null) {
            query.append(" and idProduto = :idProduto");
            parameters.and("idProduto", produtoExternoFilterDTO.getIdProduto());
        }

        if (produtoExternoFilterDTO.getIdSistema() != null) {
            query.append(" and idSistema = :idSistema");
            parameters.and("idSistema", produtoExternoFilterDTO.getIdSistema());
        }

        if (UtilString.isCharNotBlank(produtoExternoFilterDTO.getStatus())) {
            query.append(" and status = :status");
            parameters.and("status", produtoExternoFilterDTO.getStatus());
        }

        if (UtilString.isNotBlank(produtoExternoFilterDTO.getNome())) {
            query.append(" and nome like :nome");
            parameters.and("nome", "%" + produtoExternoFilterDTO.getNome() + "%");
        }

        if (UtilString.isNotBlank(produtoExternoFilterDTO.getProdutoProtheus())) {
            query.append(" and produtoProtheus = :produtoProtheus");
            parameters.and("produtoProtheus", produtoExternoFilterDTO.getProdutoProtheus());
        }

        PanacheQuery<ProdutoExterno> produtoPanacheQuery = find(query.toString(), sort, parameters);
        return PageResult.buildFromQuery(produtoExternoFilterDTO.getPage(), produtoExternoFilterDTO.getPageSize(), produtoPanacheQuery);
    }


    @Override
    public void salvarProdutoExterno(ProdutoExterno produtoExterno) {
        persist(produtoExterno);
    }

    @Override
    public void deleteProdutoExterno(ProdutoExterno produtoExterno) {
        delete(produtoExterno);
    }

}
