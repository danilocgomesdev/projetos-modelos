package fieg.modulos.cr5.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CR5_FORMASPAGTO")
public class FormaPagamento extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FORMASPAGTO")
    private Integer id;

    @Column(name = "FPG_TIPO")
    private String tipo;

    @Column(name = "FPG_VALOR")
    private BigDecimal valor;

    @Column(name = "FPG_OBS")
    private String observacao;

    @Column(name = "FPG_QUANT_PARCELAS")
    private Integer parcela;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_COBRANCAS_PAGTO")
    private PagamentoCobranca pagamentoCobranca;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public PagamentoCobranca getPagamentoCobranca() {
        return pagamentoCobranca;
    }

    public void setPagamentoCobranca(PagamentoCobranca pagamentoCobranca) {
        this.pagamentoCobranca = pagamentoCobranca;
    }

}
