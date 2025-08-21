package fieg.modulos.cadastro.impressora.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.impressora.dto.AlterarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.CriarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.ImpressoraFilterDTO;
import fieg.modulos.cadastro.impressora.model.Impressora;
import fieg.modulos.cadastro.impressora.repository.ImpressoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class ImpressoraServiceImpl implements ImpressoraService {


    @Inject
    ImpressoraRepository impressoraRepository;

    @Inject
    Mapper<CriarImpressoraDTO, Impressora> criaImpressoraMapper;

    @Inject
    Setter<AlterarImpressoraDTO, Impressora> alteraImpressoraSetter;

    @Override
    public Optional<Impressora> getByIdOptional(Integer idImpressora) {
        return impressoraRepository.getImpressoraById(idImpressora);
    }

    @Override
    public PageResult<Impressora> getAllImpressoraPaginado(ImpressoraFilterDTO filter) {
        return impressoraRepository.getAllImpressoraPaginado(filter);
    }

    @Transactional
    @Override
    public <T> T salvarNovaImpressora(CriarImpressoraDTO dto, Function<Impressora, T> mapper) {
        if (impressoraRepository.getImpressoraByIpEPorta(dto.getIpMaquina(), dto.getPorta()).isPresent()) {
            throw new NegocioException("Porta já está sendo usada nesse Endereço IP");
        }
        Impressora impresora = criaImpressoraMapper.map(dto);
        impressoraRepository.salvarImpressora(impresora);

        return mapper.apply(impresora);
    }

    @Transactional
    @Override
    public <T> T alteraImpressora(Integer idImpressora, AlterarImpressoraDTO dto, Function<Impressora, T> mapper) {
        Impressora impresora = impressoraRepository.getImpressoraById(idImpressora)
                .orElseThrow(() -> new NaoEncontradoException("Impressora não encontrada"));

        alteraImpressoraSetter.set(dto, impresora);
        impressoraRepository.salvarImpressora(impresora);

        return mapper.apply(impresora);
    }

    @Transactional
    @Override
    public void excluirImpressora(Integer idImpressora) {
        Impressora impressora = impressoraRepository.getImpressoraById(idImpressora)
                .orElseThrow(() -> new NaoEncontradoException("Impressora não encontrada"));
        try {
            impressoraRepository.deleteImpressora(impressora);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir a Impressora!");
        }
    }
}
