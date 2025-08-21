package fieg.modulos.cadastro.cliente.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.cliente.dto.AlteraPessoaCr5DTO;
import fieg.modulos.cadastro.cliente.dto.CriaPessoaCr5DTO;
import fieg.modulos.cadastro.cliente.dto.PessoaCr5DTO;
import fieg.modulos.cadastro.cliente.dto.request.*;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
class PessoaCr5Mapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<PessoaCr5, PessoaCr5DTO> pessoaCr5ToPessoaCr5DTOMapper() {
        return modelMapper.typeMap(PessoaCr5.class, PessoaCr5DTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriaPessoaCr5DTO, PessoaCr5> criaPessoaCr5DTOTOpessoaCr5Mapper() {
        return modelMapper.typeMap(CriaPessoaCr5DTO.class, PessoaCr5.class)::map;
    }

    @Singleton
    @Produces
    public Setter<AlteraPessoaCr5DTO, PessoaCr5> alteraPessoaCr5DTOTOpessoaCr5Mapper() {
        return modelMapper.typeMap(AlteraPessoaCr5DTO.class, PessoaCr5.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriaPessoaFisicaDTO, CriaPessoaCr5DTO> criaPessoaFisicaDTOTOpessoaCr5Mapper() {
        TypeMap<CriaPessoaFisicaDTO, CriaPessoaCr5DTO> typeMap = modelMapper.typeMap(CriaPessoaFisicaDTO.class, CriaPessoaCr5DTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map((pessoaFisica) -> UtilString.removeCaracterEspecial(pessoaFisica.getCpf()), CriaPessoaCr5DTO::setCpfCnpj);
            mapper.map((pessoaFisica) -> true, CriaPessoaCr5DTO::setPessoaFisica);
            mapper.map((pessoaFisica) -> false, CriaPessoaCr5DTO::setEstrangeiro);
        });
        return typeMap::map;
    }

    @Singleton
    @Produces
    public Mapper<CriaPessoaJuridicaDTO, CriaPessoaCr5DTO> criaPessoaJuridicaDTOTOpessoaCr5Mapper() {
        TypeMap<CriaPessoaJuridicaDTO, CriaPessoaCr5DTO> typeMap = modelMapper.typeMap(CriaPessoaJuridicaDTO.class, CriaPessoaCr5DTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(CriaPessoaJuridicaDTO::getRazaoSocial, CriaPessoaCr5DTO::setDescricao);
            mapper.map((pessoaJuridica) -> UtilString.removeCaracterEspecial(pessoaJuridica.getCnpj()), CriaPessoaCr5DTO::setCpfCnpj);
            mapper.map((pessoaJuridica) -> false, CriaPessoaCr5DTO::setPessoaFisica);
            mapper.map((pessoaJuridica) -> false, CriaPessoaCr5DTO::setEstrangeiro);
        });
        return typeMap::map;
    }

    @Singleton
    @Produces
    public Mapper<CriaPessoaEstrangeiroDTO, CriaPessoaCr5DTO> criaPessoaEstrangeiroDTOTOpessoaCr5Mapper() {
        TypeMap<CriaPessoaEstrangeiroDTO, CriaPessoaCr5DTO> typeMap = modelMapper.typeMap(CriaPessoaEstrangeiroDTO.class, CriaPessoaCr5DTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map((pessoaEstrangeiro) -> true, CriaPessoaCr5DTO::setEstrangeiro);
            mapper.map((pessoaEstrangeiro) -> false, CriaPessoaCr5DTO::setPessoaFisica);
        });
        return typeMap::map;
    }

    @Singleton
    @Produces
    public Mapper<AlteraPessoaFisicaDTO, AlteraPessoaCr5DTO> alteraPessoaFisicaTOalteraPessoaCr5Mapper() {
        TypeMap<AlteraPessoaFisicaDTO, AlteraPessoaCr5DTO> typeMap = modelMapper.typeMap(AlteraPessoaFisicaDTO.class, AlteraPessoaCr5DTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map((pessoaFisica) -> UtilString.removeCaracterEspecial(pessoaFisica.getCpf()), AlteraPessoaCr5DTO::setCpfCnpj);
            mapper.map((pessoaFisica) -> false, AlteraPessoaCr5DTO::setEstrangeiro);
            mapper.map((pessoaFisica) -> true, AlteraPessoaCr5DTO::setPessoaFisica);
        });
        return typeMap::map;
    }

    @Singleton
    @Produces
    public Mapper<AlteraPessoaJuridicaDTO, AlteraPessoaCr5DTO> alteraPessoaJuridicaTOalteraPessoaCr5Mapper() {
        TypeMap<AlteraPessoaJuridicaDTO, AlteraPessoaCr5DTO> typeMap = modelMapper.typeMap(AlteraPessoaJuridicaDTO.class, AlteraPessoaCr5DTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(AlteraPessoaJuridicaDTO::getRazaoSocial, AlteraPessoaCr5DTO::setDescricao);
            mapper.map((pessoaJuridica) -> UtilString.removeCaracterEspecial(pessoaJuridica.getCnpj()), AlteraPessoaCr5DTO::setCpfCnpj);
            mapper.map((pessoaJuridica) -> false, AlteraPessoaCr5DTO::setEstrangeiro);
            mapper.map((pessoaJuridica) -> false, AlteraPessoaCr5DTO::setPessoaFisica);
        });
        return typeMap::map;
    }

    @Singleton
    @Produces
    public Mapper<AlteraPessoaEstrangeiroDTO, AlteraPessoaCr5DTO> alteraPessoaEstrangeiroTOalteraPessoaCr5Mapper() {
        TypeMap<AlteraPessoaEstrangeiroDTO, AlteraPessoaCr5DTO> typeMap = modelMapper.typeMap(AlteraPessoaEstrangeiroDTO.class, AlteraPessoaCr5DTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map((pessoaEstrangeiro) -> true, AlteraPessoaCr5DTO::setEstrangeiro);
            mapper.map((pessoaEstrangeiro) -> false, AlteraPessoaCr5DTO::setPessoaFisica);
        });
        return typeMap::map;
    }


}
