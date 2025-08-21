package fieg.modulos.cadastro.cliente.rest;


import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.service.PessoaCIService;

import fieg.modulos.cadastro.cliente.dto.*;
import fieg.modulos.cadastro.cliente.dto.request.*;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import fieg.modulos.cadastro.cliente.relatorio.RelatorioClienteService;
import fieg.modulos.cadastro.cliente.service.PessoaCr5Service;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;


@Authenticated
@Path("/cliente")
@Tag(name = "Cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermissaoNecessaria({Acessos.PESSOAS})
public class PessoaCr5Rest {

    @Inject
    PessoaCr5Service pessoaCr5Service;

    @Inject
    Mapper<PessoaCr5, PessoaCr5DTO> responseMapper;

    @Inject
    Mapper<CriaPessoaFisicaDTO, CriaPessoaCr5DTO> criaPessoaFisicaMapper;

    @Inject
    Mapper<AlteraPessoaFisicaDTO, AlteraPessoaCr5DTO> alteraPessoaFisicaMapper;

    @Inject
    Mapper<CriaPessoaJuridicaDTO, CriaPessoaCr5DTO> criaPessoaJuridicaMapper;

    @Inject
    Mapper<AlteraPessoaJuridicaDTO, AlteraPessoaCr5DTO> alteraPessoaJuridicaMapper;

    @Inject
    Mapper<CriaPessoaEstrangeiroDTO, CriaPessoaCr5DTO> criaPessoaEstrangeiroMapper;

    @Inject
    Mapper<AlteraPessoaEstrangeiroDTO, AlteraPessoaCr5DTO> alteraPessoaEstrangeiroMapper;

    @Inject
    PessoaCIService pessoaCIService;

    @Inject
    RequestInfoHolder requestInfoHolder;


    @Inject
    RelatorioClienteService relatorioClienteService;




    @GET
    @Path("/{idPessoa}")
    @Operation(summary = "Busca Pessoa pela sua chave primária")
    public PessoaCr5DTO getPessoaCr5ById(
            @Parameter(description = "Id da Pessoa", example = "001")
            @PathParam("idPessoa") @NotNull Integer idPessoa
    ) {
        return pessoaCr5Service
                .getByIdOptional(idPessoa)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado"));
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todas Pessoas paginado")
    public PageResult<PessoaCr5DTO> getAllPessoaCr5Paginado(@BeanParam PessoaCr5FilterDTO pessoaCr5FilterDTO) {
        return pessoaCr5Service.getAllPessoaCr5Paginado(pessoaCr5FilterDTO).map(responseMapper::map);
    }

    @POST
    @Operation(summary = "Salva nova Pessoa Fisica")
    @Path("/pessoa-fisica")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public Integer salvaNovoPessoaFisica(CriaPessoaFisicaDTO criaPessoaFisicaDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criaPessoaFisicaDTO::setIdOperadorInclusao);
        PessoaCr5 pessoa = pessoaCr5Service.salvaNovaPessoaCr5(criaPessoaFisicaMapper.map(criaPessoaFisicaDTO));

        return pessoa.getIdPessoa();
    }

    @PUT
    @Operation(summary = "Alterar Pessoa Fisica")
    @Path("/pessoa-fisica/{idPessoa}")
    public void alteraPessoaFisica(
            @Parameter(description = "Id da Pessoa", example = "001")
            @PathParam("idPessoa") @NotNull Integer idPessoa,
            AlteraPessoaFisicaDTO alteraPessoaFisicaDTO) {
        requestInfoHolder.getIdOperador().ifPresent(alteraPessoaFisicaDTO::setIdOperadorAlteracao);
        pessoaCr5Service.updatePessoaCr5(idPessoa, alteraPessoaFisicaMapper.map(alteraPessoaFisicaDTO));
    }

    @POST
    @Operation(summary = "Salva nova Pessoa Juridica")
    @Path("/pessoa-juridica")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public Integer salvaNovoPessoaJuridica(CriaPessoaJuridicaDTO criaPessoaJuridicaDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criaPessoaJuridicaDTO::setIdOperadorInclusao);
        PessoaCr5 pessoa = pessoaCr5Service.salvaNovaPessoaCr5(criaPessoaJuridicaMapper.map(criaPessoaJuridicaDTO));

        return pessoa.getIdPessoa();
    }

    @PUT
    @Operation(summary = "Alterar Pessoa Juridica")
    @Path("/pessoa-juridica/{idPessoa}")
    public void alteraPessoaJuridica(
            @Parameter(description = "Id da Pessoa", example = "001")
            @PathParam("idPessoa") @NotNull Integer idPessoa,
            AlteraPessoaJuridicaDTO alteraPessoaJuridicaDTO) {
        requestInfoHolder.getIdOperador().ifPresent(alteraPessoaJuridicaDTO::setIdOperadorInclusao);
        pessoaCr5Service.updatePessoaCr5(idPessoa, (alteraPessoaJuridicaMapper.map(alteraPessoaJuridicaDTO)));
    }

    @POST
    @Operation(summary = "Salva nova Pessoa Estrangeira")
    @Path("/estrangeiro")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public Integer salvaNovoPessoaEstrangeiro(CriaPessoaEstrangeiroDTO criaPessoaEstrangeiroDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criaPessoaEstrangeiroDTO::setIdOperadorInclusao);
        PessoaCr5 pessoa = pessoaCr5Service.salvaNovaPessoaCr5(criaPessoaEstrangeiroMapper.map(criaPessoaEstrangeiroDTO));

        return pessoa.getIdPessoa();
    }

    @PUT
    @Operation(summary = "Alterar Pessoa Estrangeira")
    @Path("/estrangeiro/{idPessoa}")
    public void alteraPessoaEstrangeiro(
            @Parameter(description = "Id da Pessoa", example = "001")
            @PathParam("idPessoa") @NotNull Integer idPessoa,
            AlteraPessoaEstrangeiroDTO alteraPessoaEstrangeiroDTO) {
        requestInfoHolder.getIdOperador().ifPresent(alteraPessoaEstrangeiroDTO::setIdOperadorAlteracao);
        pessoaCr5Service.updatePessoaCr5(idPessoa, (alteraPessoaEstrangeiroMapper.map(alteraPessoaEstrangeiroDTO)));
    }


    @PUT
    @Path("/{idPessoa}")
    @Operation(summary = "Alterar Pessoa")
    public void alterarPessoaCr5(
            @Parameter(description = "Id da Pessoa", example = "001")
            @PathParam("idPessoa") @NotNull Integer idPessoa,
            AlteraPessoaCr5DTO alteraPessoaCr5DTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alteraPessoaCr5DTO::setIdOperadorAlteracao);
        pessoaCr5Service.updatePessoaCr5(idPessoa, alteraPessoaCr5DTO);
    }

    @DELETE
    @Path("/{idPessoa}")
    @Operation(summary = "Excluir Pessoa")
    public void excluirPessoaCr5(
            @Parameter(description = "Id da Pessoa", example = "001")
            @PathParam("idPessoa") @NotNull Integer idPessoa) {
        pessoaCr5Service.excluirPessoaCr5(idPessoa);
    }

    @GET
    @Path("/endereco")
    @Operation(summary = "Busca Endereco pelo cep")
    public EnderecoDTO getEnderecoPorCep(
            @Parameter(description = "Informe o CEP", example = "99999999")
            @QueryParam("cep") @NotNull String cep
    ) {
        return pessoaCr5Service.getEnderecoPorCep(cep).orElseThrow(() -> new NaoEncontradoException("Cep não encontrado"));
    }

    @GET
    @Path("/ci/{idPessoa}")
    @Operation(summary = "Busca pessoa (do banco compartilhado) pelo seu idPessoas do banco compartilhado")
    public PessoaCIDTO getPessoaCiById(
            @PathParam("idPessoa") Integer idPessoa
    ) {
        return pessoaCIService
                .getPessoaCIById(idPessoa)
                .orElseThrow(() -> new NaoEncontradoException("Pessoa não encontrada"));
    }

    @GET
    @Path("/pais")
    @Operation(summary = "Busca lista de Paises")
    public List<PaisDTO> getPaises() {
        return pessoaCr5Service.findPaises();
    }


    @GET
    @Path("/situacaoCliente")
    @Operation(summary = "Busca situacao do cliente no CR5")
    public PageResult<SituacaoClienteDTO> getSituacaoClientePaginado(@BeanParam ConsultaSituacaoClienteFilterDTO dto) {
           return pessoaCr5Service.getSituacaoClientePaginado(dto);
    }


    @GET
    @Path("/entidade/{idEntidade}/relatorio/filtro/pdf")
    @Operation(summary = "Gerar Relatório pdf Situacao Cliente")
    @Produces("application/pdf")
    public Response gerarRelatorioPdfSituacaoClienteFiltro(
            @BeanParam ConsultaSituacaoClienteFilterDTO dto,
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {

        PageResult<SituacaoClienteDTO> situacaoClienteDTO  = pessoaCr5Service.getSituacaoClientePaginado(dto) ;

        byte[] relatorioBytes = relatorioClienteService.gerarRelatorioSituacaoClientePdf(situacaoClienteDTO.result, idEntidade, operador, dto);
         return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_situacaoCliente.pdf").build();
    }


    @GET
    @Path("/entidade/{idEntidade}/relatorio/pdf")
    @Operation(summary = "Gerar Relatório de situação de cliente paginado")
    @Produces("application/pdf")
    public Response gerarRelatorioPdfSituacaoCliente(
            @BeanParam ConsultaSituacaoClienteFilterDTO dto,
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        List<SituacaoClienteDTO> situacaoClienteDTOList = pessoaCr5Service.getSituacaoClienteLista(dto);


        byte[] relatorioBytes = relatorioClienteService.gerarRelatorioSituacaoClientePdf(situacaoClienteDTOList, idEntidade, operador, dto);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_situacaoCliente.pdf").build();

    }


    @GET
    @Path("/entidade/{idEntidade}/relatorio/filtro/xls")
    @Operation(summary = "Gerar Xls de situação de cliente com filtros.")
    @Produces("application/excel")
    public Response gerarRelatorioXlsAgenciasFiltro(
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador,
            @BeanParam ConsultaSituacaoClienteFilterDTO dto ){

        PageResult<SituacaoClienteDTO> situacaoClienteDTO  = pessoaCr5Service.getSituacaoClientePaginado(dto) ;

        byte[] relatorioBytes = relatorioClienteService.gerarRelatorioSituacaoClienteXls(situacaoClienteDTO.result, idEntidade, operador, dto);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_situacaoCliente.xls").build();


    }

}
