package fieg.modulo.service.consulta;

import fieg.modulo.dto.response.ConsultaBoletoResponseDTO;
import fieg.modulo.dto.resquest.RequestConsultaBoletoDTO;

public interface BoletoConsultaService {

    ConsultaBoletoResponseDTO consultaBoleto(RequestConsultaBoletoDTO consultaBoletoDTO);
}
