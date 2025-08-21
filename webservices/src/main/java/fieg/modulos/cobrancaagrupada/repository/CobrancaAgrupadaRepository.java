package fieg.modulos.cobrancaagrupada.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoFiltroDTO;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;

public interface CobrancaAgrupadaRepository {

    void salvar(CobrancaAgrupada cobrancaAgrupada);

    void atualizaCobrancaAgrupada(CobrancaAgrupada cobrancaAgrupada);

    PageResult<CobrancasGrupoDTO> pesquisaUsandoFiltroGrupo(CobrancasGrupoFiltroDTO filtro);

    CobrancaAgrupada findById(Integer idCobrancaAgrupada);

    CobrancaAgrupada buscaGrupoPorIdBoleto(Integer idBoleto);

    void retiraVinculoBoleto(Integer id, Integer idOperador);
}
