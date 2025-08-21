package fieg.modulos.cobrancaagrupada.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.cobrancaagrupada.dto.CobrancaParaContratoEmRedeDTO;
import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
class CobrancaParaContratoEmRedeMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<CobrancaCliente, CobrancaParaContratoEmRedeDTO> cobrancaParaContratoEmRedeDTOMapper() {
        TypeMap<CobrancaCliente, CobrancaParaContratoEmRedeDTO> typeMap = modelMapper.typeMap(CobrancaCliente.class, CobrancaParaContratoEmRedeDTO.class);

        typeMap.addMappings(mapper -> {
                    mapper.map(
                            CobrancaCliente::getId,
                            CobrancaParaContratoEmRedeDTO::setIdCobrancaCliente
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getCobrancaAgrupada().getId(),
                            CobrancaParaContratoEmRedeDTO::setIdCobrancaAgrupada
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getId(),
                            CobrancaParaContratoEmRedeDTO::setIdInterface
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getContIdReal(),
                            CobrancaParaContratoEmRedeDTO::setContratoProducao
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getContratoProtheusSafe(),
                            CobrancaParaContratoEmRedeDTO::setContratoProtheus
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getSacadoNome(),
                            CobrancaParaContratoEmRedeDTO::setNomeResponsavelFinanceiro
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getSacadoCpfCnpj(),
                            CobrancaParaContratoEmRedeDTO::setCpfCnpjResponsavelFinanceiro
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getNumeroParcelaReal(),
                            CobrancaParaContratoEmRedeDTO::setParcela
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getSituacao(),
                            CobrancaParaContratoEmRedeDTO::setSituacao
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getDataVencimento(),
                            CobrancaParaContratoEmRedeDTO::setDataVencimento
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getValorCobranca(),
                            CobrancaParaContratoEmRedeDTO::setValorCobranca
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getJuros(),
                            CobrancaParaContratoEmRedeDTO::setJuros
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getMulta(),
                            CobrancaParaContratoEmRedeDTO::setMulta
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getTotalDebitos(),
                            CobrancaParaContratoEmRedeDTO::setJurosEMultas
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getTotalDescontos(),
                            CobrancaParaContratoEmRedeDTO::setDescontos
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getVlTotalParcela(),
                            CobrancaParaContratoEmRedeDTO::setValorTotalParcela
                    );
                }
        );

        return typeMap::map;
    }
}
