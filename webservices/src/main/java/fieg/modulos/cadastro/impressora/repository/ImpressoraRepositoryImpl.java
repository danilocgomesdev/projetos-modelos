package fieg.modulos.cadastro.impressora.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.impressora.dto.ImpressoraFilterDTO;
import fieg.modulos.cadastro.impressora.model.Impressora;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class ImpressoraRepositoryImpl implements ImpressoraRepository, PanacheRepositoryBase<Impressora, Integer> {

    @Override
    public Optional<Impressora> getImpressoraById(Integer idImpressora) {
        return findByIdOptional(idImpressora);
    }

    @Override
    public Optional<Impressora> getImpressoraByIpEPorta(String ipMaquina, Integer porta) {
        return find("ipMaquina = ?1 and porta = ?2", ipMaquina, porta).firstResultOptional();
    }

    @Override
    public PageResult<Impressora> getAllImpressoraPaginado(
            ImpressoraFilterDTO dto
    ) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("idImpressora");

        if (dto.getIdImpressora() != null) {
            queryBuilder.append(" and idImpressora = :idImpressora");
            parameters.and("idImpressora", dto.getIdImpressora());
        }

        if (dto.getIdUnidade() != null) {
            queryBuilder.append(" and idUnidade = :idUnidade");
            parameters.and("idUnidade", dto.getIdUnidade());
        }

        if (UtilString.isNotBlank(dto.getDescricao())) {
            queryBuilder.append(" and descricao like :descricao");
            parameters.and("descricao", "%" + dto.getDescricao() + "%");
        }

        if (UtilString.isNotBlank(dto.getModelo())) {
            queryBuilder.append(" and modelo like :modelo");
            parameters.and("modelo", "%" + dto.getModelo() + "%");
        }

        if (UtilString.isNotBlank(dto.getIpMaquina())) {
            queryBuilder.append(" and ipMaquina like :ipMaquina");
            parameters.and("ipMaquina", "%" + dto.getIpMaquina() + "%");
        }

        PanacheQuery<Impressora> impressoraPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(dto.getPage(), dto.getPageSize(), impressoraPanacheQuery);
    }

    @Override
    public void salvarImpressora(Impressora impressora) {
        persist(impressora);
    }

    @Override
    public void deleteImpressora(Impressora impressora) {
        delete(impressora);
    }

}
