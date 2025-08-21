package fieg.modulos.cadastro.viculodependentes.service;

import fieg.core.pagination.PageResult;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelFilterDTO;
import fieg.externos.clientewebservices.pagination.ClienteWebservicesPagination;
import fieg.modulos.cadastro.viculodependentes.dto.VinculoDependenteFilterDTO;
import fieg.modulos.cadastro.viculodependentes.model.DependenteResponsavel;
import jakarta.transaction.Transactional;

public interface VinculoDependenteService {

    PageResult<DependenteResponsavel> getAllDependentesPaginado(VinculoDependenteFilterDTO vinculoDependenteFilterDTO);

    ClienteWebservicesPagination<ClienteResponsavelDTO> getClienteResponsavel(ClienteResponsavelFilterDTO clienteResponsavelFilterDTO);

    @Transactional
    void excluirDependente(Integer idDependente);
}
