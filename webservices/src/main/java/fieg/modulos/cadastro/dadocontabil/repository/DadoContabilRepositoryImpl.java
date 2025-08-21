package fieg.modulos.cadastro.dadocontabil.repository;

import fieg.core.enums.EnumAtivoInativo;
import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilFilterDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
class DadoContabilRepositoryImpl implements DadoContabilRepository, PanacheRepositoryBase<DadoContabil, Integer> {

    @Override
    public Optional<DadoContabil> getByIdOptional(Integer idProdutoContabil) {
        return findByIdOptional(idProdutoContabil);
    }

    @Override
    public Optional<DadoContabil> getBuscaDadoContabilAnoSistemaIdProduto(Integer IdSistema, Integer idProduto, Integer ano) {
        return find("idSistema = ?1 and idProduto = ?2 and ano = ?3 ", IdSistema, idProduto, ano).firstResultOptional();
    }

    @Override
    public PageResult<DadoContabil> getAllDadoContabilPaginado(DadoContabilFilterDTO dto) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();

        if (dto.getIdDadoContabil() != null) {
            queryBuilder.append(" and idDadoContabil = :idDadoContabil");
            parameters.and("idDadoContabil", dto.getIdDadoContabil());
        }

        if (dto.getIdEntidade() != null) {
            queryBuilder.append(" and idEntidade = :idEntidade");
            parameters.and("idEntidade", dto.getIdEntidade());
        }

        if (UtilString.isNotBlank(dto.getContaContabil())) {
            queryBuilder.append(" and contaContabil = :contaContabil");
            parameters.and("contaContabil", dto.getContaContabil());
        }

        if (UtilString.isNotBlank(dto.getContaContabilDescricao())) {
            queryBuilder.append(" and contaContabilDescricao like :contaContabilDescricao");
            parameters.and("contaContabilDescricao", "%" + dto.getContaContabilDescricao() + "%");
        }

        if (UtilString.isNotBlank(dto.getItemContabil())) {
            queryBuilder.append(" and itemContabil = :itemContabil");
            parameters.and("itemContabil", dto.getItemContabil());
        }

        if (UtilString.isNotBlank(dto.getItemContabilDescricao())) {
            queryBuilder.append(" and itemContabilDescricao like :itemContabilDescricao");
            parameters.and("itemContabilDescricao", "%" + dto.getItemContabilDescricao()+ "%");
        }

        if (UtilString.isNotBlank(dto.getNatureza())) {
            queryBuilder.append(" and natureza = :natureza");
            parameters.and("natureza", dto.getNatureza());
        }

        if (UtilString.isNotBlank(dto.getNaturezaDescricao())) {
            queryBuilder.append(" and naturezaDescricao like :naturezaDescricao");
            parameters.and("naturezaDescricao","%" +dto.getNaturezaDescricao()+ "%");
        }

        if (dto.getStatus() != null) {
            queryBuilder.append(" and dataInativacao ").append(dto.getStatus() == EnumAtivoInativo.ATIVO ? "is null" : "is not null");
        }

        PanacheQuery<DadoContabil> produtoContaContabilPanacheQuery = find(queryBuilder.toString(), parameters);
        return PageResult.buildFromQuery(dto.getPage(), dto.getPageSize(), produtoContaContabilPanacheQuery);
    }

    @Override
    public void persistProdutoContaContabil(DadoContabil produtoContaContabil) {
        persist(produtoContaContabil);
    }

    @Override
    @Transactional
    public void deleteDadosContabil(DadoContabil dadoContabil) {
        delete(dadoContabil);
    }
}
