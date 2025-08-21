package fieg.modulos.cadastro.dadocontabil.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_DADO_CONTABIL")
public class DadoContabil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DADO_CONTABIL")
    private Integer idDadoContabil;

    @Column(name = "CONTA_CONTABIL")
    private String contaContabil;

    @Column(name = "CONTA_CONTABIL_DESCRICAO")
    private String contaContabilDescricao;

    @Column(name = "ITEM_CONTABIL")
    private String itemContabil;

    @Column(name = "ITEM_CONTABIL_DESCRICAO")
    private String itemContabilDescricao;

    @Column(name = "NATUREZA")
    private String natureza;

    @Column(name = "NATUREZA_DESCRICAO")
    private String naturezaDescricao;

    @Column(name = "ENTIDADE")
    private Integer idEntidade;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "DATA_INCLUSAO", columnDefinition = "datetime default getdate()")
    private LocalDateTime dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "DATA_INATIVACAO")
    private LocalDateTime dataInativacao;

    public Integer getIdDadoContabil() {
        return idDadoContabil;
    }

    public void setIdDadoContabil(Integer idDadoContabil) {
        this.idDadoContabil = idDadoContabil;
    }

    public String getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(String contaContabil) {
        this.contaContabil = contaContabil;
    }

    public String getContaContabilDescricao() {
        return contaContabilDescricao;
    }

    public void setContaContabilDescricao(String contaContabilDescricao) {
        this.contaContabilDescricao = contaContabilDescricao;
    }

    public String getItemContabil() {
        return itemContabil;
    }

    public void setItemContabil(String itemContabil) {
        this.itemContabil = itemContabil;
    }

    public String getItemContabilDescricao() {
        return itemContabilDescricao;
    }

    public void setItemContabilDescricao(String itemContabilDescricao) {
        this.itemContabilDescricao = itemContabilDescricao;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getNaturezaDescricao() {
        return naturezaDescricao;
    }

    public void setNaturezaDescricao(String naturezaDescricao) {
        this.naturezaDescricao = naturezaDescricao;
    }

    public Integer getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Integer idEntidade) {
        this.idEntidade = idEntidade;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DadoContabil that)) return false;
        return Objects.equals(getIdDadoContabil(), that.getIdDadoContabil());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDadoContabil());
    }
}
