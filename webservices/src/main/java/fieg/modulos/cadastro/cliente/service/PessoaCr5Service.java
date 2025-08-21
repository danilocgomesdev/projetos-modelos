package fieg.modulos.cadastro.cliente.service;

import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisDTO;
import fieg.modulos.cadastro.cliente.dto.*;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface PessoaCr5Service {

    Optional<PessoaCr5> getByIdOptional(Integer idPessoa);

    PageResult<PessoaCr5> getAllPessoaCr5Paginado(PessoaCr5FilterDTO pessoaCr5FilterDTO);

    @Transactional
    <T> T salvaNovaPessoaCr5(CriaPessoaCr5DTO criaPessoaCr5DTO, Function<PessoaCr5, T> mapper);

    default PessoaCr5 salvaNovaPessoaCr5(CriaPessoaCr5DTO criaPessoaCr5DTO) {
        return salvaNovaPessoaCr5(criaPessoaCr5DTO, Function.identity());
    }

    @Transactional
    <T> T updatePessoaCr5(Integer idPessoa, AlteraPessoaCr5DTO alteraPessoaCr5DTO, Function<PessoaCr5, T> mapper);

    default PessoaCr5 updatePessoaCr5(Integer idPessoa, AlteraPessoaCr5DTO alteraPessoaCr5DTO) {
        return updatePessoaCr5(idPessoa, alteraPessoaCr5DTO, Function.identity());
    }

    @Transactional
    void excluirPessoaCr5(Integer idPessoa);

    Optional<EnderecoDTO> getEnderecoPorCep(String cep);

    List<PaisDTO> findPaises();

    PageResult<SituacaoClienteDTO> getSituacaoClientePaginado(ConsultaSituacaoClienteFilterDTO consultaSituacaoClienteFilterDTO);


    public List<SituacaoClienteDTO> getSituacaoClienteLista(ConsultaSituacaoClienteFilterDTO consultaSituacaoClienteFilterDTO) ;


}


