package fieg.modulos.cadastro.contacorrente.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;
import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.cadastro.contacorrente.dto.AlterarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.dto.CriarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
class ContaCorrenteMapper {

    @Inject
    ModelMapper modelMapper;

    @Inject
    Mapper<Agencia, AgenciaDTO> agenciaMapper;

    @Inject
    Mapper<VisaoUnidade, UnidadeDTO> unidadeMapper;

    @Singleton
    @Produces
    public Mapper<ContaCorrente, ContaCorrenteDTO> contaCorrenteToContaCorrenteDTOMapper() {
        TypeMap<ContaCorrente, ContaCorrenteDTO> typeMap = modelMapper.emptyTypeMap(ContaCorrente.class, ContaCorrenteDTO.class);

        typeMap.addMappings(mapper -> {
            mapper.using(ctx -> agenciaMapper.map((Agencia) ctx.getSource()))
                    .map(
                            ContaCorrente::getAgencia,
                            ContaCorrenteDTO::setAgencia
                    );
            mapper.using(ctx -> unidadeMapper.map((VisaoUnidade) ctx.getSource()))
                    .map(
                            ContaCorrente::getUnidade,
                            ContaCorrenteDTO::setUnidade
                    );
        });

        return typeMap.implicitMappings()::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarContaCorrenteDTO, ContaCorrente> criaContaCorrenteMapper() {
        return modelMapper.typeMap(CriarContaCorrenteDTO.class, ContaCorrente.class)::map;
    }

    @Singleton
    @Produces
    public Setter<AlterarContaCorrenteDTO, ContaCorrente> alteraContaCorrenteSetter() {
        return modelMapper.typeMap(AlterarContaCorrenteDTO.class, ContaCorrente.class)::map;
    }
}
