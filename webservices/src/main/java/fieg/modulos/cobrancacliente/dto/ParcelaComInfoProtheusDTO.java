package fieg.modulos.cobrancacliente.dto;

import fieg.externos.protheus.contasareceber.dto.ContasAReceberProtheusDTO;
import lombok.Data;

@Data
public class ParcelaComInfoProtheusDTO {

    private ParcelaDTO parcelaDTO;
    private ContasAReceberProtheusDTO contasAReceberProtheus;
}
