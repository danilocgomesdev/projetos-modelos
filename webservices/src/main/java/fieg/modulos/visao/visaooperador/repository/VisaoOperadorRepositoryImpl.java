package fieg.modulos.visao.visaooperador.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorFilterDTO;
import fieg.modulos.visao.visaooperador.model.VisaoOperador;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class VisaoOperadorRepositoryImpl implements VisaoOperadorRepository, PanacheRepositoryBase<VisaoOperador, Integer> {

    @Override
    public Optional<VisaoOperador> getByIdOptional(Integer id) {
        return findByIdOptional(id);
    }


    @Override
    public PageResult<VisaoOperador> getAllVisaoOperadorPaginado(VisaoOperadorFilterDTO filterDTO) {
        StringBuilder queryBuilder = new StringBuilder(" 1 = 1 ");
        Parameters parameters = new Parameters();

        if (filterDTO.getIdOperador() != null) {
            queryBuilder.append(" and idOperador = :idOperador");
            parameters.and("idOperador", filterDTO.getIdOperador());
        }

        if (UtilString.isNotBlank(filterDTO.getUsuario())) {
            queryBuilder.append(" and usuario = :usuario");
            parameters.and("usuario", "%" + filterDTO.getUsuario() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getNome())) {
            queryBuilder.append(" and nome like :nome");
            parameters.and("nome", "%" + filterDTO.getNome() + "%");
        }

        if (UtilString.isNotBlank(filterDTO.getEmail())) {
            queryBuilder.append(" and email like :email");
            parameters.and("email", "%" + filterDTO.getEmail() + "%");
        }

        if (filterDTO.getMatricula() != null) {
            queryBuilder.append(" and matricula = :matricula");
            parameters.and("matricula", filterDTO.getMatricula());
        }

        if (filterDTO.getEntidade() != null) {
            queryBuilder.append(" and entidade = :entidade");
            parameters.and("entidade", filterDTO.getEntidade());
        }

        if (filterDTO.getIdPessoa() != null) {
            queryBuilder.append(" and idPessoa = :idPessoa");
            parameters.and("idPessoa", filterDTO.getIdPessoa());
        }

        PanacheQuery<VisaoOperador> query = find(queryBuilder.toString(), parameters);
        return PageResult.buildFromQuery(filterDTO.getPage(), filterDTO.getPageSize(), query);
    }
}
