package fieg.modulos.protheus.dto;


import java.math.BigDecimal;

public class ItemContabilDTO {

    private Integer id;
    private BigDecimal valor;
    private BigDecimal valorDesconto;
    private Integer idProduto;
    private Integer idUnidadeRecebimento;

    public ItemContabilDTO() {
    }
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

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdUnidadeRecebimento() {
        return idUnidadeRecebimento;
    }

    public void setIdUnidadeRecebimento(Integer idUnidadeRecebimento) {
        this.idUnidadeRecebimento = idUnidadeRecebimento;
    }

}
