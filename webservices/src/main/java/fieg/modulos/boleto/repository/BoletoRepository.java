package fieg.modulos.boleto.repository;

import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.CobrancaClienteGrupoDTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadoBoletoCr5DTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadosTituloBoletoCr5DTO;
import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.boleto.dto.ConsultaBoletoNossoNumeroResponseDTO;

import java.util.List;
import java.util.Optional;

public interface BoletoRepository {

    Optional<Boleto> getBoletoById(Integer idBoleto);

    Optional<ConsultaBoletoNossoNumeroResponseDTO> pesquisaUsandoNossoNumero(String nossoNumero);

    void atualizaBoleto(Boleto boleto);

    DadoBoletoCr5DTO buscarDadoBoleto(String nossoNumero);

    List<DadosTituloBoletoCr5DTO> buscarTitulosBoleto(String nossoNumero, CobrancaClienteGrupoDTO  cobrancaClienteGrupoDTO);

    CobrancaClienteGrupoDTO buscarCobrancasPorNossoNumero(String nossoNumero);
}
