package fieg.modulos.cadastro.bancos.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.bancos.dto.BancoFilterDTO;
import fieg.modulos.cadastro.bancos.model.Banco;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
class BancoRepositoryImpl implements BancoRepository, PanacheRepositoryBase<Banco, Integer> {

    @Override
    public Optional<Banco> getBancoById(Integer idBanco) {
        return findByIdOptional(idBanco);
    }

    @Override
    public Optional<Banco> getBancoByNumero(String numero) {
        return find("numero", numero).firstResultOptional();
    }

    @Override
    public PageResult<Banco> getAllBancosPaginado(
            BancoFilterDTO bancoFilterDTO
    ) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("numero");

        if (bancoFilterDTO.getId() != null) {
            queryBuilder.append(" and id = :id");
            parameters.and("id", bancoFilterDTO.getId());
        }

        if (UtilString.isNotBlank(bancoFilterDTO.getNumero())) {
            queryBuilder.append(" and numero = :numero");
            parameters.and("numero", bancoFilterDTO.getNumero());
        }

        if (UtilString.isNotBlank(bancoFilterDTO.getNome())) {
            queryBuilder.append(" and nome like :nome");
            parameters.and("nome", "%" + bancoFilterDTO.getNome() + "%");
        }

        if (UtilString.isNotBlank(bancoFilterDTO.getAbreviatura())) {
            queryBuilder.append(" and abreviatura like :abreviatura");
            parameters.and("abreviatura", "%" + bancoFilterDTO.getAbreviatura() + "%");
        }

        PanacheQuery<Banco> bancoPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(bancoFilterDTO.getPage(), bancoFilterDTO.getPageSize(), bancoPanacheQuery);
    }

    @Override
    @Transactional
    public void persistBanco(Banco banco) {
        persist(banco);
    }

    @Override
    @Transactional
    public void deleteBanco(Banco banco) {
        delete(banco);
    }
}
