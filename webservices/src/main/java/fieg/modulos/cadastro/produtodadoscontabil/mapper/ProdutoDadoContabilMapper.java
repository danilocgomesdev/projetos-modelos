package fieg.modulos.cadastro.produtodadoscontabil.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import fieg.modulos.cadastro.produtodadoscontabil.dto.AlterarProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.CriarProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.CriarVinculoProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.ProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.model.ProdutoDadoContabil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
class ProdutoDadoContabilMapper {

    @Inject
    ModelMapper modelMapper;

    @Inject
    Mapper<DadoContabil, DadoContabilDTO> dadoContabilMapper;

    @Singleton
    @Produces
    public Mapper<ProdutoDadoContabil, ProdutoDadoContabilDTO> produtoDadosContabeisToProdutoDadosContabeisDTOMapper() {
        TypeMap<ProdutoDadoContabil, ProdutoDadoContabilDTO> typeMap = modelMapper.emptyTypeMap(ProdutoDadoContabil.class, ProdutoDadoContabilDTO.class);

        typeMap.addMappings(mapper -> mapper
                .using(ctx -> {
                    DadoContabil dadoContabil = (DadoContabil) ctx.getSource();
                    return dadoContabil != null ? dadoContabilMapper.map(dadoContabil) : null;
                })
                .map(ProdutoDadoContabil::getDadoContabil, ProdutoDadoContabilDTO::setDadoContabil)
        );
        return typeMap.implicitMappings()::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarProdutoDadoContabilDTO, ProdutoDadoContabil> criarProdutoDadosContabeisDTOToProdutoDadosContabeisMapper() {
        return modelMapper.typeMap(CriarProdutoDadoContabilDTO.class, ProdutoDadoContabil.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarVinculoProdutoDadoContabilDTO, ProdutoDadoContabil> criarVinculoProdutoDadosContabeisDTOToProdutoDadosContabeisMapper() {
        return modelMapper.typeMap(CriarVinculoProdutoDadoContabilDTO.class, ProdutoDadoContabil.class)::map;
    }


    @Singleton
    @Produces
    public Setter<AlterarProdutoDadoContabilDTO, ProdutoDadoContabil> alterarProdutoDadosContabeisDTOToProdutoDadosContabeisMapper() {
        return modelMapper.typeMap(AlterarProdutoDadoContabilDTO.class, ProdutoDadoContabil.class)::map;
    }

    @Singleton
    @Produces
    public Setter<CriarVinculoProdutoDadoContabilDTO, ProdutoDadoContabil> criarVinculoProdutoDadoContabilDTOProdutoDadoContabilSetter() {
        return modelMapper.typeMap(CriarVinculoProdutoDadoContabilDTO.class, ProdutoDadoContabil.class)::map;
    }
}