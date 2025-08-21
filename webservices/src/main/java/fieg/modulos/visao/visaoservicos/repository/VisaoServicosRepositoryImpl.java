package fieg.modulos.visao.visaoservicos.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosFilterDTO;
import fieg.modulos.visao.visaoservicos.model.VisaoServicos;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class VisaoServicosRepositoryImpl implements VisaoServicosRepository, PanacheRepositoryBase<VisaoServicos, Integer> {

    @Override
    public Optional<VisaoServicos> getByIdProdutoEIdSistemaOptional(Integer idSistema, Integer idProduto) {
        return find("idSistema = ?1 and idProduto = ?2 ", idSistema, idProduto).firstResultOptional();
    }

    @Override
    public PageResult<VisaoServicos> getAllVisaoServicosPaginado(VisaoServicosFilterDTO filterDTO) {
        StringBuilder queryBuilder = new StringBuilder(" 1 = 1 ");
        Parameters parameters = new Parameters();

        if (filterDTO.getIdProduto() != null) {
            queryBuilder.append(" and idProduto = :idProduto");
            parameters.and("idProduto", filterDTO.getIdProduto());
        }

        if (filterDTO.getIdSistema() != null) {
            queryBuilder.append(" and idSistema = :idSistema");
            parameters.and("idSistema", filterDTO.getIdSistema());
        }

        if (UtilString.isNotBlank(filterDTO.getNome())) {
            queryBuilder.append(" and nome like :nome");
            parameters.and("nome", "%" + filterDTO.getNome() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getCodProdutoProtheus())) {
            queryBuilder.append(" and codProdutoProtheus = :codProdutoProtheus");
            parameters.and("codProdutoProtheus", filterDTO.getCodProdutoProtheus());
        }

        queryBuilder.append(" and status = 'A'");

        PanacheQuery<VisaoServicos> query = find(queryBuilder.toString(),  parameters);
        return PageResult.buildFromQuery(filterDTO.getPage(), filterDTO.getPageSize(), query);
    }

}
