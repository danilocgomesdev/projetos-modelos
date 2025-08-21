package fieg.modulos.interfacecobranca.service;

import fieg.modulos.interfacecobranca.model.InterfaceCobranca;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface InterfaceCobrancaService {
    Optional<InterfaceCobranca> findByIdOptional(Integer idInterface);
    void validaCobrancasMesmaProposta(List<InterfaceCobranca> interfaceCobrancas);

    void validaCobrancasMesmoResponsavel(List<InterfaceCobranca> interfaceCobrancas);

    List<InterfaceCobranca> selectInterfacesByIds(List<Integer> interfacesCobrancas);

    InterfaceCobranca findByIdCobrancaCliente(Integer idCobrancaCliente);

    void atualizaInterfaceCobranca(InterfaceCobranca interfaceCobranca);
}
