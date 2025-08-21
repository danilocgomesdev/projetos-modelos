package fieg.modulos.operacaocielo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fieg.modulos.operacaocielo.dto.URAgenda;
import fieg.modulos.operacaocielo.enums.VersaoArquivoCielo;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_RESUMO_OPERACAO_CIELO")
public class ResumoOperacaoCielo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_CABECALHO_VENDA")
    private Integer idCabecalhoVenda;

    @Column(name = "ID_CABECALHO_PAGAMENTO")
    private Integer idCabecalhoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "VERSAO_ARQUIVO")
    private VersaoArquivoCielo versao;

    @Column(name = "JSON_ORIGINAL")
    private String json;

    @Column(name = "DATA_INCLUSAO")
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "CHAVE_JOIN_DETALHE")
    private String chaveJoinDetalhe;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ResumoOperacaoCielo.class);

    static {
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCabecalhoVenda() {
        return idCabecalhoVenda;
    }

    public void setIdCabecalhoVenda(Integer idCabecalhoVenda) {
        this.idCabecalhoVenda = idCabecalhoVenda;
    }

    public Integer getIdCabecalhoPagamento() {
        return idCabecalhoPagamento;
    }

    public void setIdCabecalhoPagamento(Integer idCabecalhoPagamento) {
        this.idCabecalhoPagamento = idCabecalhoPagamento;
    }

    public VersaoArquivoCielo getVersao() {
        return versao;
    }

    public void setVersao(VersaoArquivoCielo versao) {
        this.versao = versao;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getChaveJoinDetalhe() {
        return chaveJoinDetalhe;
    }

    public void setChaveJoinDetalhe(String chaveJoinDetalhe) {
        this.chaveJoinDetalhe = chaveJoinDetalhe;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Transient
    public Object getObjetoOriginal() {
        try {
            return switch (this.versao) {
                case V15 -> mapper.readValue(this.json, URAgenda.class);
                case V14 ->
                        throw new IllegalStateException("Registros antigos V14 não deveriam estar sendo salvos nessa tabela");
            };
        } catch (IOException e) {
            throw new RuntimeException("O Json informado não pode ser convertido para o objeto original!", e);
        }
    }

    public void setObjetoOriginal(Object objetoOriginal) {
        try {
            switch (this.versao) {
                case V15 -> {
                    if (!(objetoOriginal instanceof URAgenda)) {
                        throw new RuntimeException("O Objeto informado deve ser da classe URAgenda!");
                    }
                }
                case V14 ->
                        throw new IllegalStateException("Registros antigos V14 não deveriam estar sendo salvos nessa tabela");
                default -> throw new IllegalStateException("Versao null");
            }

            this.json = mapper.writeValueAsString(objetoOriginal);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao mapear objeto da classe " + objetoOriginal.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "ResumoOperacaoCielo{" +
                "id=" + id +
                ", idCabecalhoVenda=" + idCabecalhoVenda +
                ", idCabecalhoPagamento=" + idCabecalhoPagamento +
                ", versao=" + versao +
                ", json='" + json + '\'' +
                ", dataInclusao=" + dataInclusao +
                ", chaveJoinDetalhe='" + chaveJoinDetalhe + '\'' +
                ", dataPagamento=" + dataPagamento +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResumoOperacaoCielo that = (ResumoOperacaoCielo) object;
        return Objects.equals(id, that.id)
                && Objects.equals(idCabecalhoVenda, that.idCabecalhoVenda)
                && Objects.equals(idCabecalhoPagamento, that.idCabecalhoPagamento)
                && versao == that.versao
                && Objects.equals(json, that.json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCabecalhoVenda, idCabecalhoPagamento, versao, json);
    }
}
