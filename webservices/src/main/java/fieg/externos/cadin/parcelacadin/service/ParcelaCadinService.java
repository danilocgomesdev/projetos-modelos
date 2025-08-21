package fieg.externos.cadin.parcelacadin.service;

import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.externos.cadin.parcelacadin.dto.BuscaParcelaCadinDTO;
import fieg.externos.cadin.parcelacadin.dto.ParcelaCadinDTO;
import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;

public interface ParcelaCadinService {

    ParcelaCadinDTO getParcelaCadinDTO(CobrancaCliente cobrancaCliente);

    PageResult<ParcelaDTO> getParcelasCadinSomenteParcelas(PageQuery pageQuery, BuscaParcelaCadinDTO buscaParcelaCadinDTO);
}
