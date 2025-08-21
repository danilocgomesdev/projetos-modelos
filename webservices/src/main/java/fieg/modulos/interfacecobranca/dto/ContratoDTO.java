package fieg.modulos.interfacecobranca.dto;

import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import fieg.modulos.interfacecobranca.enums.StatusInterface;
import lombok.Data;

import java.util.List;

// TODO enriquecer conforme necessário
@Data
public class ContratoDTO {

    private Integer idInterface;
    private Integer contId;
    private Integer idSistema;
    private StatusInterface statusContrato;
    private IntegraProtheus integraProtheus;
    private String sacadoNome;
    private String sacadoCpfCnpj;
    private String contratoProtheus;
    private Integer quantidadeDeParcelas;

    private List<ParcelaDTO> parcelas;
}
