package fieg.externos.protheus.itemcontabil.service;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilDTO;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilFilterDTO;
import fieg.externos.protheus.itemcontabil.repository.ItemContabilRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class itemContabilServiceImpl implements itemContabilService {

    @Inject
    ItemContabilRepository itemContabilRepository;

    @Override
    public PageResult<ItemContabilDTO> getAllitemContabilPaginado(ItemContabilFilterDTO dto) {
        return itemContabilRepository.getAllitemContabilPaginado(dto);
    }
}
