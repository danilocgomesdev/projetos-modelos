package fieg.modulos.cadastro.viculodependentes.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelFilterDTO;
import fieg.externos.clientewebservices.clienteresponsavel.service.ClienteResponsavelService;
import fieg.externos.clientewebservices.pagination.ClienteWebservicesPagination;
import fieg.modulos.cadastro.viculodependentes.dto.VinculoDependenteFilterDTO;
import fieg.modulos.cadastro.viculodependentes.model.DependenteResponsavel;
import fieg.modulos.cadastro.viculodependentes.repository.VinculoDependenteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
class VinculoDependenteServiceImpl implements VinculoDependenteService {


    @Inject
    VinculoDependenteRepository vinculoDependenteRepository;

    @Inject
    ClienteResponsavelService clienteResponsavelService;


    @Override
    public PageResult<DependenteResponsavel> getAllDependentesPaginado(VinculoDependenteFilterDTO vinculoDependenteFilterDTO) {
        return vinculoDependenteRepository.getAllDependentesPaginado(vinculoDependenteFilterDTO);
    }

    @Override
    public ClienteWebservicesPagination<ClienteResponsavelDTO> getClienteResponsavel(ClienteResponsavelFilterDTO clienteResponsavelFilterDTO) {
        if (clienteResponsavelFilterDTO.getCpf() != null) {
            clienteResponsavelFilterDTO.setCpf(UtilString.removeCaracterEspecial(clienteResponsavelFilterDTO.getCpf()));
        }
        return clienteResponsavelService.findClienteResponsavel(clienteResponsavelFilterDTO);
    }

    @Transactional
    @Override
    public void excluirDependente(Integer idDependente) {
        DependenteResponsavel dependente = vinculoDependenteRepository
                .getDependenteResponsavelById(idDependente)
                .orElseThrow(() -> new NaoEncontradoException("Dependente não encontrado"));
        try {
            vinculoDependenteRepository.deleteDependente(dependente);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir a Pessoa!");
        }
    }
}
