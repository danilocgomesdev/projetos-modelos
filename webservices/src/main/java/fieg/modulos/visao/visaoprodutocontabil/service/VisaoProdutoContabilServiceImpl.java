package fieg.modulos.visao.visaoprodutocontabil.service;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilDTO;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilFilterDTO;
import fieg.modulos.visao.visaoprodutocontabil.model.VisaoProdutoContabil;
import fieg.modulos.visao.visaoprodutocontabil.repository.VisaoProdutoContabilRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class VisaoProdutoContabilServiceImpl implements VisaoProdutoContabilService {

    @Inject
    Mapper<VisaoProdutoContabil, VisaoProdutoContabilDTO> responseMapper;

    @Inject
    VisaoProdutoContabilRepository visaoProdutoContabilRepository;

    @Override
    public Optional<VisaoProdutoContabilDTO> getVisaoProdutoContabilByIdProdutoEIdSistema(Long idSistema, Integer idProduto) {
        return visaoProdutoContabilRepository.getByIdProdutoEIdSistemaOptional(idSistema, idProduto)
                .map(responseMapper::map);
    }

    @Override
    public PageResult<VisaoProdutoContabilDTO> getAllVisaoProdutoContabilPaginado(VisaoProdutoContabilFilterDTO visaoProdutoContabilFilterDTO) {
        return visaoProdutoContabilRepository.getAllVisaoProdutoContabilPaginado(visaoProdutoContabilFilterDTO)
                .map(responseMapper::map);
    }
}
