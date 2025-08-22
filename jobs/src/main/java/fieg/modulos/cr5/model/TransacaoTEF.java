
package fieg.modulos.cr5.model;


import fieg.modulos.compartilhado.VisaoUnidade;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CR5_TRANSACAO")
@Where(clause = "TRN_STATUS <> 'Cancelado' ")
public class TransacaoTEF  extends PanacheEntityBase {


    @Id
    @Column(name = "ID_TRANSACAO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ID_FORMASPAGTO")
    private Integer cr5Formaspagto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ADQUIRENTE", updatable = false)
    private Adquirente adquirente;
    @Column(name = "TRN_FORMA_PAGAMENTO")
    private String formaPagamento;
    @Column(name = "TRN_VALOR")
    private BigDecimal trnValor;
    @Column(name = "TRN_QTDE_PARCELAS")
    private Integer trnQtdeParcelas;
    @Column(name = "TRN_DTTRANSACAO")
    private Date trnDttransacao;
    @Column(name = "TRN_OPERADORA")
    private String trnOperadora;
    @Column(name = "TRN_AUTORIZACAO")
    private String trnAutorizacao;
    @Column(name = "TRN_STATUS")
    private String trnStatus;
    @Column(name = "TRN_NUMERO_NSU")
    private String trnNumeroNsu;
    @Column(name = "TRN_MEIO_UTILIZADO")
    private String meioUtilizado;
    @Column(name = "TRN_NUMERO_CARTAO")
    private String numeroCartao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", updatable = false)
    private VisaoUnidade unidade;
    @Column(name = "ID_HEADER_ARQUIVO_CIELO")
    private Integer idArquivoCieloVenda;
    @Column(name = "TRN_TID")
    private String tid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transacao")
    private Set<TransacaoTefParc> transacaoParcelas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCr5Formaspagto() {
        return cr5Formaspagto;
    }

    public void setCr5Formaspagto(Integer cr5Formaspagto) {
        this.cr5Formaspagto = cr5Formaspagto;
    }

    public Adquirente getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(Adquirente adquirente) {
        this.adquirente = adquirente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getTrnValor() {
        return trnValor;
    }

    public void setTrnValor(BigDecimal trnValor) {
        this.trnValor = trnValor;
    }

    public Integer getTrnQtdeParcelas() {
        return trnQtdeParcelas;
    }

    public void setTrnQtdeParcelas(Integer trnQtdeParcelas) {
        this.trnQtdeParcelas = trnQtdeParcelas;
    }

    public Date getTrnDttransacao() {
        return trnDttransacao;
    }

    public void setTrnDttransacao(Date trnDttransacao) {
        this.trnDttransacao = trnDttransacao;
    }

    public String getTrnOperadora() {
        return trnOperadora;
    }

    public void setTrnOperadora(String trnOperadora) {
        this.trnOperadora = trnOperadora;
    }

    public String getTrnAutorizacao() {
        return trnAutorizacao;
    }

    public void setTrnAutorizacao(String trnAutorizacao) {
        this.trnAutorizacao = trnAutorizacao;
    }

    public String getTrnStatus() {
        return trnStatus;
    }

    public void setTrnStatus(String trnStatus) {
        this.trnStatus = trnStatus;
    }

    public String getTrnNumeroNsu() {
        return trnNumeroNsu;
    }

    public void setTrnNumeroNsu(String trnNumeroNsu) {
        this.trnNumeroNsu = trnNumeroNsu;
    }

    public String getMeioUtilizado() {
        return meioUtilizado;
    }

    public void setMeioUtilizado(String meioUtilizado) {
        this.meioUtilizado = meioUtilizado;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public VisaoUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(VisaoUnidade unidade) {
        this.unidade = unidade;
    }

    public Integer getIdArquivoCieloVenda() {
        return idArquivoCieloVenda;
    }

    public void setIdArquivoCieloVenda(Integer idArquivoCieloVenda) {
        this.idArquivoCieloVenda = idArquivoCieloVenda;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transacao")
    public Set<TransacaoTefParc> getTransacaoParcelas() {
        return transacaoParcelas;
    }
}
