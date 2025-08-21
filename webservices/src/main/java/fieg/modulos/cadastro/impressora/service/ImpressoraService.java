package fieg.modulos.cadastro.impressora.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.impressora.dto.AlterarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.CriarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.ImpressoraFilterDTO;
import fieg.modulos.cadastro.impressora.model.Impressora;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

public interface ImpressoraService {


    Optional<Impressora> getByIdOptional(Integer idImpressora);

    PageResult<Impressora> getAllImpressoraPaginado(ImpressoraFilterDTO filter);

    @Transactional
    <T> T salvarNovaImpressora(CriarImpressoraDTO dto, Function<Impressora, T> mapper);

    @Transactional
    <T> T alteraImpressora(Integer idImpressora, AlterarImpressoraDTO dto, Function<Impressora, T> mapper);

    @Transactional
    void excluirImpressora(Integer idImpressora);
}
