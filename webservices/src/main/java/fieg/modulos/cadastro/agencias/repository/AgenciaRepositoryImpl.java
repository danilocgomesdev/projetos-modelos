package fieg.modulos.cadastro.agencias.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.agencias.dto.AgenciaFilterDTO;
import fieg.modulos.cadastro.agencias.model.Agencia;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class AgenciaRepositoryImpl implements AgenciaRepository, PanacheRepositoryBase<Agencia, Integer> {

    @Override
    public Optional<Agencia> getByIdOptional(Integer idAgencia) {
        return findByIdOptional(idAgencia);
    }

    @Override
    public List<Agencia> getAll(){
        return findAll(Sort.by("id")).list();
    }

    @Override
    public Optional<Agencia> getByCNPJ(String cnpj) {
        return find("cnpj", cnpj).firstResultOptional();
    }

    @Override
    public PageResult<Agencia> getAllAgenciaPaginado(
            AgenciaFilterDTO agenciaFilterDTO
    ) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("id");

        if (UtilString.isNotBlank(agenciaFilterDTO.getId())) {
            queryBuilder.append(" and id = :id");
            parameters.and("id", agenciaFilterDTO.getId());
        }

        if (UtilString.isNotBlank(agenciaFilterDTO.getCnpj())) {
            queryBuilder.append(" and cnpj = :cnpj");
            parameters.and("cnpj", agenciaFilterDTO.getCnpj());
        }

        if (UtilString.isNotBlank(agenciaFilterDTO.getNumero())) {
            queryBuilder.append(" and numero = :numero");
            parameters.and("numero", agenciaFilterDTO.getNumero());
        }

        if (UtilString.isNotBlank(agenciaFilterDTO.getNome())) {
            queryBuilder.append(" and nome like :nome");
            parameters.and("nome", "%" + agenciaFilterDTO.getNome() + "%");
        }

        if (UtilString.isNotBlank(agenciaFilterDTO.getCidade())) {
            queryBuilder.append(" and cidade like :cidade");
            parameters.and("cidade", "%" + agenciaFilterDTO.getCidade() + "%");
        }

        if (UtilString.isNotBlank(agenciaFilterDTO.getNomeBanco())) {
            queryBuilder.append(" and banco.nome like :nomeBanco");
            parameters.and("nomeBanco", "%" + agenciaFilterDTO.getNomeBanco() + "%");
        }


        PanacheQuery<Agencia> bancoPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(agenciaFilterDTO.getPage(), agenciaFilterDTO.getPageSize(), bancoPanacheQuery);
    }

    public void persistAgencia(Agencia agencia) {
        persist(agencia);
    }

    @Transactional
    public void deleteAgencia(Agencia agencia) {
        delete(agencia);
    }

}
