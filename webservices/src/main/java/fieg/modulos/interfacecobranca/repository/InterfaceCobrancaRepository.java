package fieg.modulos.interfacecobranca.repository;

import fieg.modulos.interfacecobranca.model.InterfaceCobranca;

import java.util.List;
import java.util.Optional;

public interface InterfaceCobrancaRepository {

    Optional<InterfaceCobranca> findByIdOptional(Integer idInterface);

    List<InterfaceCobranca> selectInterfacesByIds(List interfacesCobrancas);

    InterfaceCobranca findByIdCobrancaCliente(Integer idCobrancaCliente);

    void atualizarInterfaceCobranca(InterfaceCobranca interfaceCobranca);
}
