package fieg.modulos.operadordireitos.service;

import fieg.modulos.operadordireitos.response.OperadorEDireitosDTO;
import fieg.modulos.operadordireitos.repository.OperadorDireitosRepository;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.service.PessoaCIService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
class OperadorEDireitosServiceImpl implements OperadorEDireitosService {

    @Inject
    OperadorDireitosRepository operadorDireitosRepository;

    @Inject
    PessoaCIService pessoaCIService;

    public OperadorEDireitosDTO buscarPessoaEDireitos(Integer idPessoa){
        PessoaCIDTO pessoaCIDTO = pessoaCIService.getPessoaCIById(idPessoa).orElseThrow();
        OperadorEDireitosDTO dto = new OperadorEDireitosDTO();
        dto.setDireitos(operadorDireitosRepository.getCr5(pessoaCIDTO.getIdOperador()));
        dto.setPessoa(pessoaCIDTO);
        return dto;
    }
}
