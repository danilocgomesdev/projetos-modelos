package fieg.externos.protheus.itemcontabil.service;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilDTO;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilFilterDTO;

public interface itemContabilService {

    PageResult<ItemContabilDTO> getAllitemContabilPaginado(ItemContabilFilterDTO dto);
}
