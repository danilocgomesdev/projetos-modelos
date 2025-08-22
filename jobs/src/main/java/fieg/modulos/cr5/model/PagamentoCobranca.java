package fieg.modulos.cr5.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "CR5_COBRANCAS_PAGTO")
public class PagamentoCobranca extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COBRANCAS_PAGTO")
    private Integer id;

    @Column(name = "CBP_VALORTOTAL")
    private BigDecimal valor;

    @OneToMany(mappedBy = "pagamentoCobranca", cascade = CascadeType.ALL)
    private Set<FormaPagamento> formasPagamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public Set<FormaPagamento> getFormasPagamentos() {
        return formasPagamentos;
    }
    public void setFormasPagamentos(Set<FormaPagamento> formasPagamentos) {
        this.formasPagamentos = formasPagamentos;
    }

}

