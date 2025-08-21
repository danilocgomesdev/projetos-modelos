package fieg.modulos.cobrancapagamento.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CR5_COBRANCAS_PAGTO")
public class CobrancaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COBRANCAS_PAGTO", nullable = false)
    private Integer id;

    @Column(name = "CBP_VALORTOTAL", nullable = false)
    private BigDecimal valorTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idCobrancasPagto) {
        this.id = idCobrancasPagto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
