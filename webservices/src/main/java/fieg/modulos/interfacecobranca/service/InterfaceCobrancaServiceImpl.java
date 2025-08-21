package fieg.modulos.interfacecobranca.service;

import fieg.core.exceptions.NegocioException;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.interfacecobranca.repository.InterfaceCobrancaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class InterfaceCobrancaServiceImpl implements InterfaceCobrancaService {

    @Inject
    InterfaceCobrancaRepository interfaceCobrancaRepository;

    @Override
    public Optional<InterfaceCobranca> findByIdOptional(Integer idInterface) {
        return interfaceCobrancaRepository.findByIdOptional(idInterface);
    }

    @Override
    public void validaCobrancasMesmaProposta(List<InterfaceCobranca> interfaceCobrancas) {
        Set<String> propostasCobrancas = interfaceCobrancas.stream()
                .map(i -> i.getProtheusContrato().getProposta())
                .collect(Collectors.toSet());

        if (propostasCobrancas.size() > 1) {
            throw new NegocioException("Foram selecionadas cobranças com propostas diferentes!");
        }
    }

    @Override
    public void validaCobrancasMesmoResponsavel(List<InterfaceCobranca> interfaceCobrancas) {
        Set<String> cpfCnpjCobrancas = interfaceCobrancas.stream()
                .map(InterfaceCobranca::getSacadoCpfCnpj)
                .collect(Collectors.toSet());

        if (cpfCnpjCobrancas.size() > 1) {
            throw new NegocioException("Foram selecionadas cobranças de clientes diferentes!");
        }
    }

    @Override
    public List<InterfaceCobranca> selectInterfacesByIds(List<Integer> interfacesCobrancas) {
        List<InterfaceCobranca> interfaceCobrancas = interfaceCobrancaRepository.selectInterfacesByIds(interfacesCobrancas);
        return interfaceCobrancas;
    }

    @Override
    public InterfaceCobranca findByIdCobrancaCliente(Integer idCobrancaCliente) {
        return interfaceCobrancaRepository.findByIdCobrancaCliente(idCobrancaCliente);
    }

    @Override
    public void atualizaInterfaceCobranca(InterfaceCobranca interfaceCobranca) {
        interfaceCobrancaRepository.atualizarInterfaceCobranca(interfaceCobranca);
    }
}
