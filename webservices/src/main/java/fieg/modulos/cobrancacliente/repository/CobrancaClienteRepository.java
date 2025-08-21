package fieg.modulos.cobrancacliente.repository;

import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.dto.FiltroCobrancasDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CobrancaClienteRepository {

    PageResult<CobrancaCliente> pesquisaUsandoFiltro(PageQuery pageQuery, FiltroCobrancasDTO filtro);

    PageResult<CobrancaCliente> pesquisaUsandoFiltroContratoESistema(PageQuery pageQuery, Integer contId, Integer sistemaId);

    Optional<CobrancaCliente> getByIdOptional(Integer idCobrancaCliente);

    List<CobrancaCliente> getAllById(Collection<Integer> idCobrancaCliente);

    FormaPagamentoSimplificado getFormaPagamentoSimplificada(Integer idCobrancaCliente) throws NoResultException;

    PageResult<CobrancaCliente> pesquisaUsandoFiltroProposta(CobrancaProtheusFiltroDTO filtro);

    Set<CobrancaCliente> obterCobrancasClientesPorIdInterface(Integer idInterface
            , List<Integer> listaNotInIdCobrancasClientes);

    AlterarDataVencimentoDTO buscarDadosDaCobrancaParaAlterarDataVencimento(Integer idCobrancaCliente);

    Boolean isDataValidaParaDescontoQuitacao(Integer idCobrancaGrupo, LocalDateTime novaDataVencimento);

    List<CobrancaCliente> obterCobrancaClienteIdGrupo(Integer idGrupo);

    void atualizaCobrancaCliente(CobrancaCliente cobrancaCliente);

    void salveAll(List<CobrancaCliente> adicionarCobrancaClientes);

    void deletarPorId(Integer id);

    CobrancaCliente buscaCobrancaClientePorIdBoleto(Integer idBoleto);

    void retiraVinculoBoleto(Integer id, Integer idOperador);
}
