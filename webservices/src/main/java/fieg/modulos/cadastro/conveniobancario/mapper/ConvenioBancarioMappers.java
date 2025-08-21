package fieg.modulos.cadastro.conveniobancario.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.cadastro.conveniobancario.dto.AlterarConvenioBancarioDTO;
import fieg.modulos.cadastro.conveniobancario.dto.ConvenioBancarioDTO;
import fieg.modulos.cadastro.conveniobancario.dto.CriarConvenioBancarioDTO;
import fieg.modulos.cadastro.conveniobancario.dto.FaixaNossoNumeroDTO;
import fieg.modulos.cadastro.conveniobancario.enums.Moeda;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;
import fieg.modulos.cadastro.conveniobancario.model.FaixaNossoNumero;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

class ConvenioBancarioMappers {

    @Inject
    ModelMapper modelMapper;

    @Inject
    Mapper<ContaCorrente, ContaCorrenteDTO> contaCorrenteContaCorrenteDTOMapper;

    @Inject
    Mapper<VisaoUnidade, UnidadeDTO> unidadeMapper;

    @ApplicationScoped
    public Mapper<FaixaNossoNumero, FaixaNossoNumeroDTO> faixaNossoNumeroFaixaNossoNumeroDTOMapper() {
        TypeMap<FaixaNossoNumero, FaixaNossoNumeroDTO> typeMap = modelMapper.typeMap(FaixaNossoNumero.class, FaixaNossoNumeroDTO.class);

        return typeMap.implicitMappings()::map;
    }

    @ApplicationScoped
    public Mapper<ConvenioBancario, ConvenioBancarioDTO> convenioBancarioContaCorrenteDTOMapper(
            Mapper<FaixaNossoNumero, FaixaNossoNumeroDTO> faixaMapper
    ) {
        TypeMap<ConvenioBancario, ConvenioBancarioDTO> typeMap = modelMapper.emptyTypeMap(ConvenioBancario.class, ConvenioBancarioDTO.class);

        typeMap.addMappings(mapper -> {
            mapper.using(ctx -> {
                        @SuppressWarnings("unchecked")
                        var faixas = (List<FaixaNossoNumero>) ctx.getSource();

                        return faixas.stream().map(faixaMapper::map).collect(Collectors.toList());
                    })
                    .map(
                            ConvenioBancario::getFaixasNossoNumero,
                            ConvenioBancarioDTO::setFaixasNossoNumero
                    );

            mapper.using(ctx -> (contaCorrenteContaCorrenteDTOMapper.map((ContaCorrente) ctx.getSource())))
                    .map(
                            ConvenioBancario::getContaCorrente,
                            ConvenioBancarioDTO::setContaCorrente
                    );

            mapper.using(ctx -> ((Moeda) ctx.getSource()).paraDTO())
                    .map(
                            ConvenioBancario::getMoeda,
                            ConvenioBancarioDTO::setMoeda
                    );

            mapper.using(ctx -> unidadeMapper.map((VisaoUnidade) ctx.getSource()))
                    .map(
                            ConvenioBancario::getUnidade,
                            ConvenioBancarioDTO::setUnidade
                    );
        });

        return typeMap.implicitMappings()::map;
    }

    @ApplicationScoped
    public Mapper<CriarConvenioBancarioDTO, ConvenioBancario> criarConvenioBancarioDTOConvenioBancarioMapper() {
        return modelMapper.typeMap(CriarConvenioBancarioDTO.class, ConvenioBancario.class)::map;
    }

    @ApplicationScoped
    public Setter<AlterarConvenioBancarioDTO, ConvenioBancario> alterarConvenioBancarioDTOConvenioBancarioSetter() {
        return modelMapper.typeMap(AlterarConvenioBancarioDTO.class, ConvenioBancario.class)::map;
    }
}
