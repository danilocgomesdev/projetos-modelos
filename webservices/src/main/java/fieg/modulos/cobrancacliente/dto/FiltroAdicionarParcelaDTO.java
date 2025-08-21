package fieg.modulos.cobrancacliente.dto;

import lombok.Data;

@Data
public class FiltroAdicionarParcelaDTO {
    private Integer idCobrancaCliente;
    private Integer idInterfaceCobranca;
    private Integer qtdParcela;
    private Integer idOperadorInclusao;
}
