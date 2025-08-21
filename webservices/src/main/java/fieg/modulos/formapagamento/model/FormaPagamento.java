package fieg.modulos.formapagamento.model;

import fieg.modulos.cobrancapagamento.model.CobrancaPagamento;
import fieg.modulos.formapagamento.enums.FormaPagamentoTipo;
import fieg.modulos.pedido.model.Pedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_FORMASPAGTO")
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FORMASPAGTO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COBRANCAS_PAGTO")
    private CobrancaPagamento cobrancaPagamento;

    @Column(name = "FPG_TIPO", nullable = false, length = 20)
    @Size(max = 20, message = "Tamanho do Campo: tipo")
    private String tipo;

    @Column(name = "FPG_VALOR")
    private BigDecimal valor;

    @Column(name = "FPG_OBS", length = 5000)
    @Size(max = 5000, message = "Tamanho do Campo: obs")
    private String observacao;

    @Column(name = "FPG_QUANT_PARCELAS")
    private Integer quantidadeParcelas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PEDIDO")
    private Pedido pedido;

    @Column(name = "FPG_DT_INTEGRACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataIntegracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idFormasPagto) {
        this.id = idFormasPagto;
    }

    public CobrancaPagamento getCobrancaPagamento() {
        return cobrancaPagamento;
    }

    public void setCobrancaPagamento(CobrancaPagamento cobrancaPagamento) {
        this.cobrancaPagamento = cobrancaPagamento;
    }

    // Deixar somente para o Hibernate, usar variantes com enum
    @Deprecated
    public String getTipo() {
        return tipo;
    }

    @Deprecated
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public FormaPagamentoTipo getTipoEnum() {
        return FormaPagamentoTipo.getByValorBanco(tipo);
    }

    public void setTipoEnum(FormaPagamentoTipo tipo) {
        this.tipo = tipo.getValorBanco();
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

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantParcelas) {
        this.quantidadeParcelas = quantParcelas;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDateTime getDataIntegracao() {
        return dataIntegracao;
    }

    public void setDataIntegracao(LocalDateTime dataIntegracao) {
        this.dataIntegracao = dataIntegracao;
    }
}
