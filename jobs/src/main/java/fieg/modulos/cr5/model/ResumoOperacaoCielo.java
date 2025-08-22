package fieg.modulos.cr5.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import fieg.modulos.cieloJobs.arquivo.v15.URAgenda;
import fieg.modulos.cr5.enums.VersaoArquivoCielo;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CR5_RESUMO_OPERACAO_CIELO")
public class ResumoOperacaoCielo extends PanacheEntityBase {

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
    private Date dataInclusao;

    @Column(name = "CHAVE_JOIN_DETALHE")
    private String chaveJoinDetalhe;

    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;

    private static final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
    private static final Logger logger = LoggerFactory.getLogger(ResumoOperacaoCielo.class);

    static {
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

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getChaveJoinDetalhe() {
        return chaveJoinDetalhe;
    }

    public void setChaveJoinDetalhe(String chaveJoinDetalhe) {
        this.chaveJoinDetalhe = chaveJoinDetalhe;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
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
        } catch (JsonProcessingException e) {
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
            }
            this.json = mapper.writeValueAsString(objetoOriginal);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao mapear objeto da classe " + objetoOriginal.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }
}
