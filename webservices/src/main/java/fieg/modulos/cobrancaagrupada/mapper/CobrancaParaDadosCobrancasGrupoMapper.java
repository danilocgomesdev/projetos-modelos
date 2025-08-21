package fieg.modulos.cobrancaagrupada.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.cobrancaagrupada.dto.CobrancaParaContratoEmRedeDTO;
import fieg.modulos.cobrancaagrupada.dto.DadosCobrancasGrupoDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
public class CobrancaParaDadosCobrancasGrupoMapper {
    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<CobrancaCliente, DadosCobrancasGrupoDTO> cobrancaParaDadosCobrancasGrupoMapper() {
        TypeMap<CobrancaCliente, DadosCobrancasGrupoDTO> typeMap = modelMapper.typeMap(CobrancaCliente.class, DadosCobrancasGrupoDTO.class);

        typeMap.addMappings(mapper -> {
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getContIdReal(),
                            DadosCobrancasGrupoDTO::setContrato
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getContratoProtheusSafe(),
                            DadosCobrancasGrupoDTO::setProtheusContrato

                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getConsumidorNome(),
                            DadosCobrancasGrupoDTO::setConsumidorNome
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getUnidade().getCodigoUnidade(),
                            DadosCobrancasGrupoDTO::setIdUnidade
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getNumeroParcelaReal(),
                            DadosCobrancasGrupoDTO::setParcela
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getInterfaceCobranca().getIdSistema(),
                            DadosCobrancasGrupoDTO::setIdSistema
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getValorCobranca(),
                            DadosCobrancasGrupoDTO::setValorCobranca
                    );
                    mapper.map(
                            (cobrancaCliente) -> cobrancaCliente.getBoleto() == null ? null : cobrancaCliente.getBoleto().getNossoNumero(),
                            DadosCobrancasGrupoDTO::setNossoNumero
                    );
                    mapper.map(
                            (cobrancaCliente) ->  cobrancaCliente.getSituacao(),
                            DadosCobrancasGrupoDTO::setSituacao
                    );
                }
        );

        return typeMap::map;
    }
}
