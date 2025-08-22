package fieg.modulo.service.manutencao;

import fieg.modulo.dto.response.ManutencaoBoletoResponseDTO;
import fieg.modulo.dto.resquest.RequestBaixarBoletoDTO;
import fieg.modulo.dto.resquest.RequestManutencaoBoletoDTO;

public interface BoletoManutencaoService {

    ManutencaoBoletoResponseDTO incluirBoleto(RequestManutencaoBoletoDTO dto);

    ManutencaoBoletoResponseDTO alterarBoleto(RequestManutencaoBoletoDTO dto);

    ManutencaoBoletoResponseDTO baixarBoleto(RequestBaixarBoletoDTO dto);
}
