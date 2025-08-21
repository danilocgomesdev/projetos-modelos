package fieg.modulos.cobrancacliente.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancaagrupada.dto.AlteraDadosGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoFiltroDTO;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.dto.FiltroAdicionarParcelaDTO;
import fieg.modulos.cobrancacliente.dto.FiltroCobrancasDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CobrancaClienteService {


    PageResult<CobrancaCliente> pesquisaUsandoFiltro(PageQuery pageQuery, FiltroCobrancasDTO filtro);

    PageResult<CobrancaCliente> pesquisaUsandoFiltroContratoESistema(PageQuery pageQuery, Integer contId, Integer sistemaId);

    Optional<CobrancaCliente> getByIdOptional(Integer idCobrancaCliente);

    CobrancaCliente getSeExistir(Integer idCobrancaCliente) throws NaoEncontradoException;

    List<CobrancaCliente> getAllById(Collection<Integer> idCobrancaCliente);

    FormaPagamentoSimplificado getFormaPagamentoSimplificada(Integer idCobrancaCliente) throws NoResultException;

    PageResult<CobrancaCliente> pesquisaUsandoFiltroProposta( CobrancaProtheusFiltroDTO filtro);

    Set<CobrancaCliente> obterCobrancasClientesPorIdInterface(Integer idInterface
            , List<Integer> listaNotInIdCobrancasClientes);

    AlterarDataVencimentoDTO buscarDadosDaCobrancaParaAlterarDataVencimento(Integer idCobrancaCliente);

    Boolean isDataValidaParaDescontoQuitacao(Integer idCobrancaGrupo, LocalDateTime novaDataVencimento);

    Set<CobrancaCliente> atualizaDataCobrancaCliente(Set<CobrancaCliente> listaCobrancaCliente, LocalDateTime novaDataVencimento, Integer idOperador);

    Set<CobrancaCliente> atualizaDadosCobrancaCliente(Set<CobrancaCliente> listaCobrancaCliente, AlteraDadosGrupoDTO alteraDadosGrupoDTO);

    List<CobrancaCliente> obterCobrancaClienteIdGrupo(Integer idGrupo);

    void atualizaCobrancaCliente(CobrancaCliente cobrancaCliente);

    void cancelarBoleto(Integer idCobrancaCliente, Integer idOperador);

    void adicionarParcela(FiltroAdicionarParcelaDTO adicionarParcelaDTO);

    void excluirCobrancaCliente(Integer idCobrancaCliente, Integer idOperador);

    CobrancaCliente buscaCobrancaClientePorIdBoleto(Integer idBoleto);

    void retiraVinculoBoleto(Integer id, Integer idOperador);
}
