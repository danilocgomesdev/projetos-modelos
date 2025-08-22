package fieg.modulos.cieloJobs.arquivo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;

import java.beans.Transient;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArquivoRetornoDTO {

    @JsonIgnore
    private Path path;

    private String nomeArquivo;

    private List<DetalheComprovanteVendaDTO> comprovantesVendaTef = new ArrayList<>();

    private List<DetalheResumoOperacaoDTO> detalhesResumoOperacaoTef = new ArrayList<>();

    //************ Todos os registros do arquivo ************
    private List<DetalheComprovanteVendaDTO> comprovantesVenda;

    private List<DetalheResumoOperacaoDTO> detalhesResumoOperacao;
    //************ Todos os registros do arquivo ************

    @JsonIgnore
    private Integer idArquivoCielo;

    private CabecalhoDTO cabecalho;

    @JsonIgnore
    private SumarioDTO sumarioDTO;

    @JsonIgnore
    private Set<ResumoOperacaoDTO> resumosOperacoes = new HashSet<>();

    @JsonIgnore
    private List<DetalheComprovanteVendaDTO> comprovantesVendaPOS = new ArrayList<>();

    @JsonIgnore
    private Set<ResumoOperacaoDTO> resumosOperacoesPOS = new HashSet<>();

    @JsonIgnore
    private List<DetalheResumoOperacaoDTO> detalhesResumoOperacaoPOS = new ArrayList<>();

    public ArquivoRetornoDTO(CabecalhoEArquivo cabecalhoEArquivo) {
        this.cabecalho = cabecalhoEArquivo.getCabecalho();
        this.nomeArquivo = cabecalho.getNomeArquivo();
        this.path = cabecalhoEArquivo.getPathArquivoLido();

        carregarInformacoes(cabecalhoEArquivo.getLinhasRestantes());
        vincularDetalheOperacao();
        vincularDetalheOperacaoPOS();
        adicionarTodosRegistros();
    }

    private void carregarInformacoes(List<String> linhas) {
        Map<String, String> mapCodigoTipo = new HashMap<>();

        for (String linha : linhas) {
            if (linha.charAt(0) == '9') {
                sumarioDTO = new SumarioDTO(linha, nomeArquivo);
            }

            if (linha.charAt(0) == '1') {
                DetalheResumoOperacaoDTO detalheResumoOperacaoDTO = new DetalheResumoOperacaoDTO(linha, nomeArquivo);
                if (detalheResumoOperacaoDTO.isPagamentoTefeComerceOUCieloLio()) {
                    detalhesResumoOperacaoTef.add(detalheResumoOperacaoDTO);
                    mapCodigoTipo.put(detalheResumoOperacaoDTO.getNumeroUnicoRO(), "TEF");
                } else {
                    detalhesResumoOperacaoPOS.add(detalheResumoOperacaoDTO);
                    mapCodigoTipo.put(detalheResumoOperacaoDTO.getNumeroUnicoRO(), "POS");
                }
            }
            if (linha.charAt(0) == '2') {
                DetalheComprovanteVendaDTO transacao = new DetalheComprovanteVendaDTO(linha, nomeArquivo, isArquivoPagamento());

                String tipo = mapCodigoTipo.get(transacao.getNumeroUnicoRO());
                if ("TEF".equals(tipo)) {//|| "ECOMMERCE".equals(tipo)
                    comprovantesVendaTef.add(transacao);
                } else {
                    comprovantesVendaPOS.add(transacao);
                }
            }

        }

    }

    public CabecalhoDTO getCabecalho() {
        return cabecalho;
    }

    public boolean isSemComprovanteVenda() {
        return comprovantesVendaTef == null || comprovantesVendaTef.isEmpty();
    }

    public List<DetalheComprovanteVendaDTO> getComprovantesVendaTef() {
        return comprovantesVendaTef;
    }

    public void setComprovantesVendaTef(List<DetalheComprovanteVendaDTO> comprovantesVendaTef) {
        this.comprovantesVendaTef = comprovantesVendaTef;
    }

    public boolean isSemDetalhesResumoOperacao() {
        return detalhesResumoOperacaoTef == null || detalhesResumoOperacaoTef.isEmpty();
    }

    public List<DetalheResumoOperacaoDTO> getDetalhesResumoOperacaoTef() {
        return detalhesResumoOperacaoTef;
    }

    public void setDetalhesResumoOperacaoTef(List<DetalheResumoOperacaoDTO> detalhesResumoOperacaoTef) {
        this.detalhesResumoOperacaoTef = detalhesResumoOperacaoTef;
    }

    public SumarioDTO getSumario() {
        return sumarioDTO;
    }

    public Set<ResumoOperacaoDTO> getResumosOperacoes() {
        return resumosOperacoes;
    }

    public void setResumosOperacoes(Set<ResumoOperacaoDTO> resumosOperacoes) {
        this.resumosOperacoes = resumosOperacoes;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Integer getIdArquivoCielo() {
        return idArquivoCielo;
    }

    public void setIdArquivoCielo(Integer idArquivoCielo) {
        this.idArquivoCielo = idArquivoCielo;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getOpcaoExtrato() {
        return cabecalho.getOpcaoExtrato();
    }

    private void vincularDetalheOperacao() {
        vincularDetalheOperacaogenerico(
                detalhesResumoOperacaoTef,
                resumosOperacoes,
                comprovantesVendaTef,
                this::obterResumoOperacao
        );
    }

    private void vincularDetalheOperacaoPOS() {
        vincularDetalheOperacaogenerico(
                detalhesResumoOperacaoPOS,
                resumosOperacoesPOS,
                comprovantesVendaPOS,
                this::obterResumoOperacaoPOS
        );
    }

    private void vincularDetalheOperacaogenerico(
            List<DetalheResumoOperacaoDTO> operacoesDTOs,
            Set<ResumoOperacaoDTO> resumosOperacoes,
            List<DetalheComprovanteVendaDTO> comprovantesVenda,
            Function<String, Optional<ResumoOperacaoDTO>> resumoOperacaoDTOFunction
    ) {
        Map<String, List<DetalheResumoOperacaoDTO>> map = operacoesDTOs.stream().collect(
                Collectors.groupingBy(DetalheResumoOperacaoDTO::getNumeroUnicoRO));

        for (Map.Entry<String, List<DetalheResumoOperacaoDTO>> entry : map.entrySet()) {
            List<DetalheResumoOperacaoDTO> detalhes = entry.getValue();

            ResumoOperacaoDTO resumoOperacaoDTO = new ResumoOperacaoDTO(detalhes.get(0));
            resumosOperacoes.add(resumoOperacaoDTO);

            resumoOperacaoDTO.setDetalhesResumoOperacao(detalhes);
        }

        if (resumosOperacoes.isEmpty()) {
            return;
        }

        //**********************************************************************
        Map<String, List<DetalheComprovanteVendaDTO>> mapVendas = comprovantesVenda.stream().collect(
                Collectors.groupingBy(DetalheComprovanteVendaDTO::getNumeroUnicoRO));

        for (Map.Entry<String, List<DetalheComprovanteVendaDTO>> entry : mapVendas.entrySet()) {
            String numeroUnicoRO = entry.getKey();
            List<DetalheComprovanteVendaDTO> vendas = entry.getValue();

            resumoOperacaoDTOFunction.apply(numeroUnicoRO).ifPresent(resumoOperacaoDTO -> {
                for (DetalheComprovanteVendaDTO venda : vendas) {
                    venda.setMeioCaptura(resumoOperacaoDTO.getMeioCaptura());
                }
                resumoOperacaoDTO.setDetalhesComprovantesVenda(vendas);
            });
        }
    }

    private Optional<ResumoOperacaoDTO> obterResumoOperacao(String numeroUnicoRO) {
        return obterResumoOperacaoGenerico(resumosOperacoes, numeroUnicoRO);
    }

    private Optional<ResumoOperacaoDTO> obterResumoOperacaoPOS(String numeroUnicoRO) {
        return obterResumoOperacaoGenerico(resumosOperacoesPOS, numeroUnicoRO);
    }

    // Pode retornar empty para POS
    private Optional<ResumoOperacaoDTO> obterResumoOperacaoGenerico(Set<ResumoOperacaoDTO> resumos, String numeroUnicoRO) {
        return resumos.stream().filter(resumo -> resumo.getNumeroUnicoRO().equals(numeroUnicoRO)).findFirst();
    }

    @Override
    public String toString() {
        return "ArquivoRetornoDTO{" + "nomeArquivo=" + nomeArquivo + ", cabecalhoDTO=" + cabecalho + ", comprovantesVenda=" + comprovantesVendaTef + '}';
    }

    @Transient
    public boolean isArquivoVenda() {
        return cabecalho.isVenda();
    }

    @Transient
    public boolean isArquivoPagamento() {
        return cabecalho.isPagamento();
    }

    public Set<ResumoOperacaoDTO> getResumosOperacoesPOS() {
        return resumosOperacoesPOS;
    }

    public void setResumosOperacoesPOS(Set<ResumoOperacaoDTO> resumosOperacoesPOS) {
        this.resumosOperacoesPOS = resumosOperacoesPOS;
    }

    public List<DetalheResumoOperacaoDTO> getDetalhesResumoOperacaoPOS() {
        return detalhesResumoOperacaoPOS;
    }

    public void setDetalhesResumoOperacaoPOS(List<DetalheResumoOperacaoDTO> detalhesResumoOperacaoPOS) {
        this.detalhesResumoOperacaoPOS = detalhesResumoOperacaoPOS;
    }

    public List<DetalheComprovanteVendaDTO> getComprovantesVendaPOS() {
        return comprovantesVendaPOS;
    }

    public List<DetalheComprovanteVendaDTO> getComprovantesVenda() {
        return comprovantesVenda;
    }

    public void setComprovantesVenda(List<DetalheComprovanteVendaDTO> comprovantesVenda) {
        this.comprovantesVenda = comprovantesVenda;
    }

    public List<DetalheResumoOperacaoDTO> getDetalhesResumoOperacao() {
        return detalhesResumoOperacao;
    }

    public void setDetalhesResumoOperacao(List<DetalheResumoOperacaoDTO> detalhesResumoOperacao) {
        this.detalhesResumoOperacao = detalhesResumoOperacao;
    }

    private void adicionarTodosRegistros() {
        detalhesResumoOperacao = new ArrayList<>(detalhesResumoOperacaoTef);
        comprovantesVenda = new ArrayList<>(comprovantesVendaTef);

        if (!detalhesResumoOperacaoPOS.isEmpty()) {
            detalhesResumoOperacao.addAll(detalhesResumoOperacaoPOS);
        }
        if (!comprovantesVendaPOS.isEmpty()) {
            comprovantesVenda.addAll(comprovantesVendaPOS);
        }
    }

}
