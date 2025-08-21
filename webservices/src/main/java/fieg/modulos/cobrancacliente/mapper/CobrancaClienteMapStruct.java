package fieg.modulos.cobrancacliente.mapper;

import fieg.core.mapstruct.MapStructComponentModel;
import fieg.modulos.cobrancacliente.dto.CobrancaClienteAdicionarDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MapStructComponentModel.JAKARTA )
public interface CobrancaClienteMapStruct {

    CobrancaClienteMapStruct INSTANCE = Mappers.getMapper(CobrancaClienteMapStruct.class);
    @Mapping(target = "id", ignore = true)
    CobrancaCliente toNewCobrancaCliente(CobrancaCliente cobrancaCliente);

    @Mapping(source = "numeroParcela", target = "numParcela")
    @Mapping(source = "numeroParcelaReal", target = "numParcelaProtheus")
    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "valorCobranca", target = "valorCobranca")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "interfaceCobranca.id", target = "idInterface")
    @Mapping(source = "id", target = "idCobrancaCliente")
    @Mapping(source = "unidade.codigoUnidade", target = "codigoUnidade")
    @Mapping(source = "idSistema", target = "idSistema")
    @Mapping(source = "interfaceCobranca.sacadoNome", target = "sacadoNome")
    @Mapping(source = "interfaceCobranca.sacadoCpfCnpj", target = "sacadoCpfCnpj")
    @Mapping(source = "unidade.entidade.descReduzida", target = "nomeEntidade",
            defaultExpression = "java(null)")
    @Mapping(source = "boleto.id", target = "idBoleto")
    @Mapping(source = "interfaceCobranca.protheusContrato.contratoProtheus", target = "contratoProtheus",
            defaultExpression = "java(\"\")")
    CobrancaClienteAdicionarDTO toDto(CobrancaCliente cobrancaCliente);

}
