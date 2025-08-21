package fieg.externos.clientewebservices.clienteresponsavel.service;

import fieg.core.interfaces.Mapper;
import fieg.externos.clientewebservices.ClienteWebservicesRestClient;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelFilterDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelResponseDTO;
import fieg.externos.clientewebservices.pagination.ClienteWebservicesPagination;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
class ClienteResponsavelServiceImpl implements ClienteResponsavelService {

    @Inject
    @RestClient
    ClienteWebservicesRestClient clienteWebservicesRestClient;

    @Inject
    Mapper<ClienteResponsavelResponseDTO, ClienteResponsavelDTO> responseMapper;


    @Override
    public ClienteWebservicesPagination<ClienteResponsavelDTO> findClienteResponsavel(ClienteResponsavelFilterDTO filter) {
        return clienteWebservicesRestClient.findClienteResponsavel(filter).map(responseMapper::map);
    }
}
