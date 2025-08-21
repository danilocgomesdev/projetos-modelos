package fieg.modulos.boleto.model;

import fieg.modulos.boleto.enums.SituacaoBoleto;
import fieg.modulos.boleto.enums.SituacaoBoletoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_BOLETOS")
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BOLETOS", nullable = false)
    private Integer id;

    @Column(name = "BOL_DTDOCUMENTO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataDocumento;

    @Column(name = "BOL_DTVENCIMENTO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataVencimento;

    @Column(name = "BOL_VLDOCUMENTO", nullable = false)
    private BigDecimal valorDocumento;

    @Column(name = "BOL_NOSSONUMERO", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: nossoNumero")
    private String nossoNumero;

    @Column(name = "BOL_ANOREF", nullable = false)
    private Integer anoReferencia;

    @Column(name = "BOL_MESREF", nullable = false)
    private Integer mesReferencia;

    @Column(name = "BOL_OBS1", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: observacao1")
    private String observacao1;

    @Column(name = "BOL_OBS2", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: observacao2")
    private String observacao2;

    @Column(name = "BOL_OBS3", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: observacao3")
    private String observacao3;

    @Column(name = "BOL_OBS4", length = 3000)
    @Size(max = 255, message = "Tamanho do Campo: observacao4")
    private String observacao4;

    @Column(name = "BOL_OBS5", length = 3000)
    @Size(max = 255, message = "Tamanho do Campo: observacao5")
    private String observacao5;

    @Column(name = "BOL_LOCALPAGTO", nullable = false, length = 100)
    @Size(max = 100, message = "Tamanho do Campo: localPagamento")
    private String localPagamento;

    @Column(name = "BOL_CONTADOR", nullable = false)
    private Integer contador = 0;

    @Column(name = "BOL_SITUACAOPGTO", nullable = false, length = 10)
    @Convert(converter = SituacaoBoletoConverter.class)
    private SituacaoBoleto situacaoPagamento;

    @Column(name = "ID_UNIDADE")
    private Integer idUnidade;

    @Column(name = "DATA_CANCELAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCancelamento;

    @Column(name = "ID_OPERADOR_CANCELAMENTO")
    private Integer idOperadorCancelamento;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "IDENTIFICACAO_ARQUIVO_BAIXA", length = 40)
    @Size(max = 20, message = "Tamanho do Campo: identificacaoArquivoBaixa")
    private String identificacaoArquivoBaixa;

    // TODO serve para algo? Pode ser removido?
    @Column(name = "STATUS_REMESSA", nullable = false, length = 3, columnDefinition = "varchar(3) default 'ENV'")
    @Size(max = 20, message = "Tamanho do Campo: statusRemessa")
    private String statusRemessa = "ENV";

    @Column(name = "BOL_DTVENCIMENTO_INICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataVencimentoInicial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idBoletos) {
        this.id = idBoletos;
    }

    public LocalDateTime getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(LocalDateTime dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorDocumento() {
        return valorDocumento;
    }

    public void setValorDocumento(BigDecimal valorDocumento) {
        this.valorDocumento = valorDocumento;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public Integer getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(Integer mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public String getObservacao1() {
        return observacao1;
    }

    public void setObservacao1(String observacao1) {
        this.observacao1 = observacao1;
    }

    public String getObservacao2() {
        return observacao2;
    }

    public void setObservacao2(String observacao2) {
        this.observacao2 = observacao2;
    }

    public String getObservacao3() {
        return observacao3;
    }

    public void setObservacao3(String observacao3) {
        this.observacao3 = observacao3;
    }

    public String getObservacao4() {
        return observacao4;
    }

    public void setObservacao4(String observacao4) {
        this.observacao4 = observacao4;
    }

    public String getObservacao5() {
        return observacao5;
    }

    public void setObservacao5(String observacao5) {
        this.observacao5 = observacao5;
    }

    public String getLocalPagamento() {
        return localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public SituacaoBoleto getSituacaoPagamento() {
        return situacaoPagamento;
    }

    public void setSituacaoPagamento(SituacaoBoleto situacaoPagamento) {
        this.situacaoPagamento = situacaoPagamento;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Integer getIdOperadorCancelamento() {
        return idOperadorCancelamento;
    }

    public void setIdOperadorCancelamento(Integer idOperadorCancelamento) {
        this.idOperadorCancelamento = idOperadorCancelamento;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public String getIdentificacaoArquivoBaixa() {
        return identificacaoArquivoBaixa;
    }

    public void setIdentificacaoArquivoBaixa(String identificacaoArquivoBaixa) {
        this.identificacaoArquivoBaixa = identificacaoArquivoBaixa;
    }

    public String getStatusRemessa() {
        return statusRemessa;
    }

    public void setStatusRemessa(String statusRemessa) {
        this.statusRemessa = statusRemessa;
    }

    public LocalDateTime getDataVencimentoInicial() {
        return dataVencimentoInicial;
    }

    public void setDataVencimentoInicial(LocalDateTime dataVencimentoInicial) {
        this.dataVencimentoInicial = dataVencimentoInicial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boleto boleto = (Boleto) o;
        return Objects.equals(id, boleto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
