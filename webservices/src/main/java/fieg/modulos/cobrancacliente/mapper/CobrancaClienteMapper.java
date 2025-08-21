package fieg.modulos.cobrancacliente.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.cobrancacliente.dto.CobrancaClienteAdicionarDTO;
import fieg.modulos.cobrancacliente.dto.CriaCobrancaClienteDTO;
import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
@Named("modelMapper")
class CobrancaClienteMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    @Named("cobrancaClienteAdicionarDTOMapper")
    public Mapper<CobrancaCliente, CobrancaClienteAdicionarDTO> cobrancaClienteAdicionarDTOMapper() {
        TypeMap<CobrancaCliente, CobrancaClienteAdicionarDTO> typeMap = modelMapper.typeMap(CobrancaCliente.class, CobrancaClienteAdicionarDTO.class);

        typeMap.addMappings(mapper -> {
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getNumeroParcela(),
                            CobrancaClienteAdicionarDTO::setNumParcela
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getNumeroParcelaReal(),
                            CobrancaClienteAdicionarDTO::setNumParcelaProtheus
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getSituacao(),
                            CobrancaClienteAdicionarDTO::setSituacao
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getValorCobranca(),
                            CobrancaClienteAdicionarDTO::setValorCobranca
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getDataVencimento(),
                            CobrancaClienteAdicionarDTO::setDataVencimento
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getId(),
                            CobrancaClienteAdicionarDTO::setIdInterface
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getId(),
                            CobrancaClienteAdicionarDTO::setIdCobrancaCliente
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getUnidade().getCodigoUnidade(),
                            CobrancaClienteAdicionarDTO::setCodigoUnidade
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getIdSistema(),
                            CobrancaClienteAdicionarDTO::setIdSistema
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getSacadoNome(),
                            CobrancaClienteAdicionarDTO::setSacadoNome
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getSacadoCpfCnpj(),
                            CobrancaClienteAdicionarDTO::setSacadoCpfCnpj
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getUnidade().getEntidade() != null ?  cobrancaCliente.getUnidade().getEntidade().descReduzida : null,
                            CobrancaClienteAdicionarDTO::setNomeEntidade
                    );
                }
        );

        return typeMap::map;
    }

    @Singleton
    @Produces
    @Named("cobrancaClienteParcelaDTOMapper")
    public Mapper<CobrancaCliente, ParcelaDTO> cobrancaClienteParcelaDTOMapper() {
        TypeMap<CobrancaCliente, ParcelaDTO> typeMap = modelMapper.typeMap(CobrancaCliente.class, ParcelaDTO.class);

        typeMap.addMappings(mapper -> {
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getId(),
                            ParcelaDTO::setIdInterface
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getContIdReal(),
                            ParcelaDTO::setContId
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getStatusInterface(),
                            ParcelaDTO::setStatusContrato
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getIntegraProtheus(),
                            ParcelaDTO::setIntegraProtheus
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getSacadoNome(),
                            ParcelaDTO::setSacadoNome
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getSacadoCpfCnpj(),
                            ParcelaDTO::setSacadoCpfCnpj
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getContratoProtheusSafe(),
                            ParcelaDTO::setContratoProtheus
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getQuantidadeRealDeParcelas(),
                            ParcelaDTO::setQuantidadeDeParcelas
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getUnidade().getId(),
                            ParcelaDTO::setIdUnidade
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getUnidade().getCodigoUnidade(),
                            ParcelaDTO::setCodigoUnidade
                    );
                    mapper.map(
                            CobrancaCliente::getId,
                            ParcelaDTO::setIdCobrancaCliente
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getUnidade().getEntidade(),
                            ParcelaDTO::setEntidade
                    );
                }
        );

        return typeMap::map;
    }

    @Produces
    public TypeMap<CobrancaCliente, CriaCobrancaClienteDTO> cobrancaClienteToDTOMapper() {
        return modelMapper.typeMap(CobrancaCliente.class, CriaCobrancaClienteDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getConvenioBancario().getId(), CriaCobrancaClienteDTO::setIdConvenioBancario);
                    mapper.map(src -> src.getPessoa().getIdPessoa(), CriaCobrancaClienteDTO::setIdPessoa);
                });
    }
}
