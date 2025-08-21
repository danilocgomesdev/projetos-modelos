package fieg.modulos.cadastro.impressora.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.impressora.dto.ImpressoraFilterDTO;
import fieg.modulos.cadastro.impressora.model.Impressora;

import java.util.Optional;

public interface ImpressoraRepository {

    Optional<Impressora> getImpressoraById(Integer idImpressora);

    Optional<Impressora> getImpressoraByIpEPorta(String ipMaquina, Integer porta);

    PageResult<Impressora> getAllImpressoraPaginado(ImpressoraFilterDTO dto);

    void salvarImpressora(Impressora impressora);

    void deleteImpressora(Impressora impressora);
}
