package fieg.externos.compartilhadoservice.pessoa.service;

import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIFilterDTO;

import java.util.Optional;

public interface PessoaCIService {

    Optional<PessoaCIDTO> getPessoaCIById(Integer idPessoa);

    PageResult<PessoaCIDTO> getPessoaCIPaginado(PessoaCIFilterDTO idPessoa);
}
