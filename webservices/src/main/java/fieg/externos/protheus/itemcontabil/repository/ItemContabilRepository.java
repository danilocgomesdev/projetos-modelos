package fieg.externos.protheus.itemcontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilDTO;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilFilterDTO;

public interface ItemContabilRepository {

    PageResult<ItemContabilDTO> getAllitemContabilPaginado(ItemContabilFilterDTO dto);
}
