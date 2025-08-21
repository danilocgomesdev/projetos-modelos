package fieg.modulos.visao.visaooperador.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorDTO;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorFilterDTO;
import fieg.modulos.visao.visaooperador.model.VisaoOperador;
import fieg.modulos.visao.visaooperador.repository.VisaoOperadorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
class VisaoOperadorServiceImpl implements VisaoOperadorService {


    @Inject
    Mapper<VisaoOperador, VisaoOperadorDTO> responseMapper;


    @Inject
    VisaoOperadorRepository visaoOperadorRepository;

    @Override
    public Optional<VisaoOperadorDTO> getVisaoOperadorDTOByIdOperador(Integer idOperador) {
        return visaoOperadorRepository.getByIdOptional(idOperador).map(responseMapper::map);

    }

    @Override
    public VisaoOperadorDTO getSeExistir(Integer idOperador) throws NaoEncontradoException {
        return getVisaoOperadorDTOByIdOperador(idOperador)
                .orElseThrow(() -> new NaoEncontradoException("Operador de id %d não encontrado".formatted(idOperador)));
    }

    @Override
    public PageResult<VisaoOperadorDTO> getAllVisaoOperadorPaginado(VisaoOperadorFilterDTO visaoOperadorFilterDTO) {
        return visaoOperadorRepository.getAllVisaoOperadorPaginado(visaoOperadorFilterDTO).map(responseMapper::map);
    }


    @Override
    public List<VisaoOperadorDTO> getAllVisaoOperadorPaginadoList(List<Integer> idOperador) {
        List<VisaoOperadorDTO> list = new ArrayList<>();
        for (Integer id : idOperador) {
            if(id != null) {
                visaoOperadorRepository.getByIdOptional(id)
                        .map(responseMapper::map)
                        .ifPresent(list::add);
            }
        }
        return list;
    }

}
