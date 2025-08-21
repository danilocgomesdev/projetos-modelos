package fieg.externos.caixaboletoservice.consulta.service;

import fieg.externos.caixaboletoservice.baixa.dto.ManutencaoBoletoResponseDTO;
import fieg.externos.caixaboletoservice.consulta.dto.ConsultaBoletoCaixaResponseDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;


public interface CaixaBoletoService {

    ConsultaBoletoCaixaResponseDTO consultaBoletoCaixa(BoletoFilterDTO filter) ;

    ManutencaoBoletoResponseDTO baixaBoletoCaixa(BoletoFilterDTO filter);


}
