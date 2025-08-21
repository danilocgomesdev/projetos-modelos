package fieg.externos.compartilhadoservice.endereco.service;

import fieg.externos.compartilhadoservice.endereco.dto.EnderecoDTO;

import java.util.Optional;

public interface EnderecoService {

    Optional<EnderecoDTO> getEnderecoPorCep(String cep);
}
