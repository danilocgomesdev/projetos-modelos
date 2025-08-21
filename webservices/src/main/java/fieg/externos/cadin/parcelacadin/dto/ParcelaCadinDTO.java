package fieg.externos.cadin.parcelacadin.dto;

import fieg.modulos.cobrancacliente.dto.ParcelaComInfoProtheusDTO;
import lombok.Data;

import java.util.List;

@Data
public class ParcelaCadinDTO {

    private ParcelaComInfoProtheusDTO parcelaCadin;
    private List<InformacoesOrigemDTO> informacoesOrigem;
}
