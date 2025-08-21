package fieg.modulos.visao.visaoservicos.service;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosDTO;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosFilterDTO;
import fieg.modulos.visao.visaoservicos.model.VisaoServicos;
import fieg.modulos.visao.visaoservicos.repository.VisaoServicosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
class VisaoServicosServiceImpl implements VisaoServicosService {


    @Inject
    Mapper<VisaoServicos, VisaoServicosDTO> responseMapper;

    @Inject
    VisaoServicosRepository visaoServicosRepository;

    @Override
    public Optional<VisaoServicosDTO> getVisaoServicosByIdProdutoEIdSistema(Integer idSistema, Integer idProduto) {
        return visaoServicosRepository.getByIdProdutoEIdSistemaOptional( idSistema,  idProduto).map(responseMapper::map);

    }

    @Override
    public PageResult<VisaoServicosDTO> getAllVisaoServicosPaginado(VisaoServicosFilterDTO visaoServicosFilterDTO) {
        return visaoServicosRepository.getAllVisaoServicosPaginado(visaoServicosFilterDTO).map(responseMapper::map);
    }


}
