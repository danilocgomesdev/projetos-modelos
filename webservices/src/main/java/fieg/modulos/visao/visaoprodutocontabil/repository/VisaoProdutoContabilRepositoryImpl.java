package fieg.modulos.visao.visaoprodutocontabil.repository;


import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilFilterDTO;
import fieg.modulos.visao.visaoprodutocontabil.model.VisaoProdutoContabil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class VisaoProdutoContabilRepositoryImpl implements VisaoProdutoContabilRepository, PanacheRepositoryBase<VisaoProdutoContabil, Integer> {

    @Override
    public Optional<VisaoProdutoContabil> getByIdProdutoEIdSistemaOptional(Long idSistema, Integer idProduto) {
        return find("idSistema = ?1 and idProduto = ?2", idSistema, idProduto).firstResultOptional();
    }

    @Override
    public PageResult<VisaoProdutoContabil> getAllVisaoProdutoContabilPaginado(VisaoProdutoContabilFilterDTO filterDTO) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1 ");
        Parameters parameters = new Parameters();

        if (filterDTO.getIdProdutoDadoContabil() != null) {
            queryBuilder.append(" and idProdutoDadoContabil = :idProdutoDadoContabil");
            parameters.and("idProdutoDadoContabil", filterDTO.getIdProdutoDadoContabil());
        }

        if (filterDTO.getIdProduto() != null) {
            queryBuilder.append(" and idProduto = :idProduto");
            parameters.and("idProduto", filterDTO.getIdProduto());
        }

        if (UtilString.isNotBlank(filterDTO.getProduto())) {
            queryBuilder.append(" and produto like :produto");
            parameters.and("produto", "%" + filterDTO.getProduto() + "%");
        }

        if (filterDTO.getIdSistema() != null) {
            queryBuilder.append(" and idSistema = :idSistema");
            parameters.and("idSistema", filterDTO.getIdSistema());
        }

        if (UtilString.isNotBlank(filterDTO.getSistema())) {
            queryBuilder.append(" and sistema like :sistema");
            parameters.and("sistema", "%" + filterDTO.getSistema() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getStatus())) {
            queryBuilder.append(" and status = :status");
            parameters.and("status",  filterDTO.getStatus());
        }

        if (UtilString.isNotBlank(filterDTO.getDmed())) {
            queryBuilder.append(" and dmed = :dmed");
            parameters.and("dmed", filterDTO.getDmed());
        }

        if (UtilString.isNotBlank(filterDTO.getCodProdutoProtheus())) {
            queryBuilder.append(" and codProdutoProtheus like :codProdutoProtheus");
            parameters.and("codProdutoProtheus", filterDTO.getCodProdutoProtheus() + "%");
        }

        if (filterDTO.getPreco() != null) {
            queryBuilder.append(" and preco = :preco");
            parameters.and("preco", filterDTO.getPreco());
        }

        if (filterDTO.getIdDadoContabil() != null) {
            queryBuilder.append(" and idDadoContabil = :idDadoContabil");
            parameters.and("idDadoContabil", filterDTO.getIdDadoContabil());
        }

        if (UtilString.isNotBlank(filterDTO.getContaContabil())) {
            queryBuilder.append(" and contaContabil like :contaContabil");
            parameters.and("contaContabil", "%" + filterDTO.getContaContabil() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getContaContabilDescricao())) {
            queryBuilder.append(" and contaContabilDescricao like :contaContabilDescricao");
            parameters.and("contaContabilDescricao", "%" + filterDTO.getContaContabilDescricao() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getItemContabil())) {
            queryBuilder.append(" and itemContabil like :itemContabil");
            parameters.and("itemContabil", "%" + filterDTO.getItemContabil() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getItemContabilDescricao())) {
            queryBuilder.append(" and itemContabilDescricao like :itemContabilDescricao");
            parameters.and("itemContabilDescricao", "%" + filterDTO.getItemContabilDescricao() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getNatureza())) {
            queryBuilder.append(" and natureza like :natureza");
            parameters.and("natureza", "%" + filterDTO.getNatureza() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getNaturezaDescricao())) {
            queryBuilder.append(" and naturezaDescricao like :naturezaDescricao");
            parameters.and("naturezaDescricao", "%" + filterDTO.getNaturezaDescricao() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getEntidade())) {
            queryBuilder.append(" and entidade like :entidade");
            parameters.and("entidade", "%" + filterDTO.getEntidade() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getNomeEntidade())) {
            queryBuilder.append(" and nomeEntidade like :nomeEntidade");
            parameters.and("nomeEntidade", "%" + filterDTO.getNomeEntidade() + "%");
        }

        if (filterDTO.getOperadorInclusaoProduto() != null) {
            queryBuilder.append(" and operadorInclusaoProduto = :operadorInclusaoProduto");
            parameters.and("operadorInclusaoProduto", filterDTO.getOperadorInclusaoProduto());
        }

        if (filterDTO.getOperadorAlteracaoProduto() != null) {
            queryBuilder.append(" and operadorAlteracaoProduto = :operadorAlteracaoProduto");
            parameters.and("operadorAlteracaoProduto", filterDTO.getOperadorAlteracaoProduto());
        }

        if (filterDTO.getOperadorInclusaoDadoContabil() != null) {
            queryBuilder.append(" and operadorInclusaoDadoContabil = :operadorInclusaoDadoContabil");
            parameters.and("operadorInclusaoDadoContabil", filterDTO.getOperadorInclusaoDadoContabil());
        }

        if (filterDTO.getOperadorAlteracaoDadoContabil() != null) {
            queryBuilder.append(" and operadorAlteracaoDadoContabil = :operadorAlteracaoDadoContabil");
            parameters.and("operadorAlteracaoDadoContabil", filterDTO.getOperadorAlteracaoDadoContabil());
        }

        PanacheQuery<VisaoProdutoContabil> query = find(queryBuilder.toString(), parameters);
        return PageResult.buildFromQuery(filterDTO.getPage(), filterDTO.getPageSize(), query);
    }


}
