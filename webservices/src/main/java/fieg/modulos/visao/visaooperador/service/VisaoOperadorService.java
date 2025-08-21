package fieg.modulos.visao.visaooperador.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorDTO;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorFilterDTO;

import java.util.List;
import java.util.Optional;

public interface VisaoOperadorService {


    Optional<VisaoOperadorDTO> getVisaoOperadorDTOByIdOperador(Integer idOperador);

    VisaoOperadorDTO getSeExistir(Integer idOperador) throws NaoEncontradoException;

    PageResult<VisaoOperadorDTO> getAllVisaoOperadorPaginado(VisaoOperadorFilterDTO visaoOperadorFilterDTO);

    List<VisaoOperadorDTO> getAllVisaoOperadorPaginadoList(List<Integer> idOperador);
}
