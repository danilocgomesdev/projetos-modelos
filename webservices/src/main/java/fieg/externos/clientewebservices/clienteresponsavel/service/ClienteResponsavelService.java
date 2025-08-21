package fieg.externos.clientewebservices.clienteresponsavel.service;

import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelFilterDTO;
import fieg.externos.clientewebservices.pagination.ClienteWebservicesPagination;

public interface ClienteResponsavelService {

    ClienteWebservicesPagination<ClienteResponsavelDTO> findClienteResponsavel(ClienteResponsavelFilterDTO filter);
}
