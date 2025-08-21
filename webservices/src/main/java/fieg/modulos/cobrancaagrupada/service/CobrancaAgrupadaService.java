package fieg.modulos.cobrancaagrupada.service;


import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancaagrupada.dto.AlteraDadosGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoFiltroDTO;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CobrancaAgrupadaService {

    PageResult<CobrancaCliente>  pesquisaUsandoFiltroProposta(CobrancaProtheusFiltroDTO cobrancaProtheusFiltroDTO);

    PageResult<CobrancasGrupoDTO> pesquisaUsandoFiltroGrupo(CobrancasGrupoFiltroDTO filtro);

    void agruparParcelasProtheus(List<Integer> listaIdInterface, LocalDate dataVencimento, Integer operadorId);

    void agruparParcelas(List<Integer> listaIdsCobrancaCliente, LocalDateTime dataVencimento, Integer idOperadorInclusao);

    void alterarDataVencimentoGrupo(Integer idCobrancaAgrupada, String dataVencimento, Integer idOperador);

    void atualizaCobrancaAgrupada(CobrancaAgrupada cobrancaAgrupada);

    void alterarDadosGrupo(Integer idCobrancaAgrupada, AlteraDadosGrupoDTO alterarDadosGrupoDTO);

    void desfazerGrupo(Integer idGrupo, Integer idOperador);

    List<CobrancaCliente> obterCobracasClienteIdGrupo(Integer idCobrancaAgrupada);

    void cancelarBoletoGrupo(Integer idCobrancaAgrupada, Integer idOperador);

    void salvar(CobrancaAgrupada cobrancaAgrupada);

    CobrancaAgrupada buscaGrupoPorIdBoleto(Integer idBoleto);

    void retiraVinculoBoleto(Integer id, Integer idOperador);
}
