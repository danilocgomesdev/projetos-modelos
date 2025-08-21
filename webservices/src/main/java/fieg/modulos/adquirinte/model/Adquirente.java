package fieg.modulos.adquirinte.model;

import fieg.core.enums.AtivoInativoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_ADQUIRENTE")
public class Adquirente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADQUIRENTE", nullable = false)
    private Integer id;

    @Column(name = "ADQ_DESCRICAO", nullable = false, length = 30)
    @Size(max = 30, message = "Tamanho do Campo: descricao")
    private String descricao;

    @Column(name = "ADQ_STATUS", nullable = false, columnDefinition = "char(1)")
    @Convert(converter = AtivoInativoConverter.class)
    private Boolean ativo;

    @Column(name = "ADQ_VARIACAO_VENCIMENTO", nullable = false)
    private Integer variacaoVencimento;

    @Column(name = "ADQ_TAXA_DEBITO")
    private BigDecimal taxaDebito;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "ADQ_DTINCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "ADQ_DTALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ADQ_VARIACAO_VENCIMENTO_DEBITO")
    private Integer variacaoVencimentoDebito;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getVariacaoVencimento() {
        return variacaoVencimento;
    }

    public void setVariacaoVencimento(Integer variacaoVencimento) {
        this.variacaoVencimento = variacaoVencimento;
    }

    public BigDecimal getTaxaDebito() {
        return taxaDebito;
    }

    public void setTaxaDebito(BigDecimal taxaDebito) {
        this.taxaDebito = taxaDebito;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getVariacaoVencimentoDebito() {
        return variacaoVencimentoDebito;
    }

    public void setVariacaoVencimentoDebito(Integer variacaoVencimentoDebito) {
        this.variacaoVencimentoDebito = variacaoVencimentoDebito;
    }
}

