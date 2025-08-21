package fieg.modulos.cadastro.produtoexterno.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.produtoexterno.dto.AlterarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.CriarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.model.ProdutoExterno;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class ProdutoExternoMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<ProdutoExterno, ProdutoExternoDTO> produtoToProdutoDTOMapper() {
        return modelMapper.typeMap(ProdutoExterno.class, ProdutoExternoDTO.class)::map;
    }
    @Singleton
    @Produces
    public Mapper<CriarProdutoExternoDTO, ProdutoExterno> criaProdutoDTOToProdutoMapper() {
        return modelMapper.typeMap(CriarProdutoExternoDTO.class, ProdutoExterno.class)::map;
    }
    @Singleton
    @Produces
    public Setter<AlterarProdutoExternoDTO, ProdutoExterno> alteraProdutoDTOToProdutoMapper() {
        return modelMapper.typeMap(AlterarProdutoExternoDTO.class, ProdutoExterno.class)::map;
    }

}
