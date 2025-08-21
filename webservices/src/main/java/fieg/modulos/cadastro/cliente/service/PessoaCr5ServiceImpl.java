package fieg.modulos.cadastro.cliente.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoDTO;
import fieg.externos.compartilhadoservice.endereco.service.EnderecoService;
import fieg.externos.compartilhadoservice.pais.dto.PaisDTO;
import fieg.externos.compartilhadoservice.pais.service.PaisService;
import fieg.modulos.cadastro.cliente.dto.*;
import fieg.modulos.cadastro.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import fieg.modulos.cadastro.cliente.repository.PessoaCr5Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class PessoaCr5ServiceImpl implements PessoaCr5Service {

    @Inject
    PessoaCr5Repository pessoaCr5Repository;

    @Inject
    EnderecoService enderecoService;

    @Inject
    PaisService paisService;

    @Inject
    Mapper<CriaPessoaCr5DTO, PessoaCr5> mapperCriaPessoaCr5DTO;

    @Inject
    Setter<AlteraPessoaCr5DTO, PessoaCr5> setterAlteraPessoaCr5DTO;

    @Override
    public Optional<PessoaCr5> getByIdOptional(Integer idPessoa) {
        return pessoaCr5Repository.getByIdOptional(idPessoa);
    }

    @Override
    public PageResult<PessoaCr5> getAllPessoaCr5Paginado(PessoaCr5FilterDTO pessoaCr5FilterDTO) {
        return pessoaCr5Repository.getAllPessoaCr5Paginado(pessoaCr5FilterDTO);
    }

    @Transactional
    @Override
    public <T> T salvaNovaPessoaCr5(CriaPessoaCr5DTO criaPessoaCr5DTO, Function<PessoaCr5, T> mapper) {
        Optional<PessoaCr5> pessoaCr5Optional = pessoaCr5Repository
                .getPessoaCr5ByCpf(criaPessoaCr5DTO.getCpfCnpj());
        if (pessoaCr5Optional.isPresent()) {
            throw new ValorInvalidoException("A Pessoa já está cadastrada!");
        }
        PessoaCr5 pessoaCr5 = mapperCriaPessoaCr5DTO.map(criaPessoaCr5DTO);
        pessoaCr5Repository.persistPessoaCr5(pessoaCr5);

        return mapper.apply(pessoaCr5);
    }

    @Transactional
    @Override
    public <T> T updatePessoaCr5(Integer idPessoa, AlteraPessoaCr5DTO alteraPessoaCr5DTO, Function<PessoaCr5, T> mapper) {
        PessoaCr5 pessoaCr5 = pessoaCr5Repository
                .getByIdOptional(idPessoa)
                .orElseThrow(() -> new NaoEncontradoException("Pessoa não encontrada"));
        setterAlteraPessoaCr5DTO.set(alteraPessoaCr5DTO, pessoaCr5);
        pessoaCr5.setDataAlteracao(LocalDateTime.now());
        pessoaCr5Repository.persistPessoaCr5(pessoaCr5);

        return mapper.apply(pessoaCr5);
    }

    @Transactional
    @Override
    public void excluirPessoaCr5(Integer idPessoa) {
        PessoaCr5 pessoaCr5 = pessoaCr5Repository
                .getByIdOptional(idPessoa)
                .orElseThrow(() -> new NaoEncontradoException("Pessoa não encontrada"));
        try {
            pessoaCr5Repository.deletePessoaCr5(pessoaCr5);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir a Pessoa!");
        }
    }

    @Override
    public Optional<EnderecoDTO> getEnderecoPorCep(String cep) {
        return enderecoService.getEnderecoPorCep(cep);
    }

    @Override
    public List<PaisDTO> findPaises(){
        return paisService.findPaises();
    }


    @Override
    public PageResult<SituacaoClienteDTO> getSituacaoClientePaginado(ConsultaSituacaoClienteFilterDTO consultaSituacaoClienteFilterDTO) {
        return pessoaCr5Repository.pesquisaSituacaoCliente(consultaSituacaoClienteFilterDTO);
    }


    @Override
    public List<SituacaoClienteDTO> getSituacaoClienteLista(ConsultaSituacaoClienteFilterDTO consultaSituacaoClienteFilterDTO) {

        return pessoaCr5Repository.pesquisaSituacaoClienteLista(consultaSituacaoClienteFilterDTO);

    }


}
