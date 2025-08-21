package fieg.modulos.cadastro.conveniobancario.model;


import fieg.core.enums.SimNaoConverter;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.cadastro.conveniobancario.enums.Moeda;
import fieg.modulos.cadastro.conveniobancario.enums.MoedaConverter;
import fieg.modulos.cadastro.conveniobancario.enums.SistemaBancario;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

// TODO talvez também criar Enums e DTOs para TituloEspecie e TipoEmissao, mas são sempre fixos até o momento
@Entity
@Table(name = "CR5_CONVENIOSBANCARIOS")
public class ConvenioBancario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONVENIOSBANCARIOS", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CONTASCORRENTES", nullable = false)
    private ContaCorrente contaCorrente;

    @Column(name = "CBA_NOMECEDENTE", nullable = false, length = 60)
    @Size(max = 60, message = "Tamanho do Campo: nomeCedente")
    private String nomeCedente;

    @Column(name = "CBA_NUMERO", nullable = false, length = 10)
    @Size(max = 10, message = "Tamanho do Campo: numero")
    private String numero;

    @Column(name = "CBA_CARTEIRA", nullable = false, length = 5)
    @Size(max = 5, message = "Tamanho do Campo: carteira")
    private String carteira;

    @Column(name = "CBA_MOEDA", nullable = false, length = 5)
    @Convert(converter = MoedaConverter.class)
    private Moeda moeda;

    @Column(name = "CBA_TITULOESPECIE", nullable = false, length = 5)
    @Size(max = 5, message = "Tamanho do Campo: tituloEspecie")
    private String tituloEspecie;

    @Column(name = "CBA_TIPOEMISSAO", nullable = false, length = 5)
    @Size(max = 5, message = "Tamanho do Campo: tipoEmissao")
    private String tipoEmissao;

    @Column(name = "CBA_ACEITE", nullable = false, columnDefinition = "char")
    @Convert(converter = SimNaoConverter.class)
    private Boolean aceite;

    @Column(name = "CBA_INDICEMULTA", nullable = false)
    private Float indiceMulta;

    @Column(name = "CBA_INDICEJUROS", nullable = false)
    private Float indiceJuros;

    @Column(name = "CBA_SISTEMABANCARIO", nullable = false, columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    private SistemaBancario sistemaBancario;

    @Column(name = "CBA_OBS1", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cbaObs1")
    private String observacao1;

    @Column(name = "CBA_OBS2", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cbaObs2")
    private String observacao2;

    @Column(name = "CBA_OBS3", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cbaObs3")
    private String observacao3;

    @Column(name = "CBA_OBS4", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cbaObs4")
    private String observacao4;

    @Column(name = "CBA_OBS5", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cbaObs5")
    private String observacao5;

    @Column(name = "CBA_LOCALPAGTO", nullable = false, length = 100)
    @Size(max = 100, message = "Tamanho do Campo: localPagamento")
    private String localPagamento;

    @Column(name = "ID_UNIDADE", nullable = false)
    private Integer idUnidade;

    // Usar a coluna de id para atualizações/inserir!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", insertable = false, updatable = false)
    private VisaoUnidade unidade;

    @Column(name = "DATA_INATIVO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataInativo;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "UTILIZA_UN_CENTRALIZADORA", columnDefinition = "char")
    @Convert(converter = SimNaoConverter.class)
    private Boolean utilizaUnCentralizadora = false;

    @OneToMany(mappedBy = "convenioBancario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FaixaNossoNumero> faixasNossoNumero;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getNomeCedente() {
        return nomeCedente;
    }

    public void setNomeCedente(String nomeCedente) {
        this.nomeCedente = nomeCedente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getTituloEspecie() {
        return tituloEspecie;
    }

    public void setTituloEspecie(String tituloEspecie) {
        this.tituloEspecie = tituloEspecie;
    }

    public String getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(String tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public Boolean getAceite() {
        return aceite;
    }

    public void setAceite(Boolean aceite) {
        this.aceite = aceite;
    }

    public Float getIndiceMulta() {
        return indiceMulta;
    }

    public void setIndiceMulta(Float indiceMulta) {
        this.indiceMulta = indiceMulta;
    }

    public Float getIndiceJuros() {
        return indiceJuros;
    }

    public void setIndiceJuros(Float indiceJuros) {
        this.indiceJuros = indiceJuros;
    }

    public SistemaBancario getSistemaBancario() {
        return sistemaBancario;
    }

    public void setSistemaBancario(SistemaBancario sistemaBancario) {
        this.sistemaBancario = sistemaBancario;
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

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public VisaoUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(VisaoUnidade unidade) {
        this.unidade = unidade;
    }

    public LocalDateTime getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(LocalDateTime dataInativo) {
        this.dataInativo = dataInativo;
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

    public Boolean getUtilizaUnCentralizadora() {
        return utilizaUnCentralizadora;
    }

    public void setUtilizaUnCentralizadora(Boolean utilizaUnCentralizadora) {
        this.utilizaUnCentralizadora = utilizaUnCentralizadora;
    }

    public List<FaixaNossoNumero> getFaixasNossoNumero() {
        return faixasNossoNumero;
    }

    public void setFaixasNossoNumero(List<FaixaNossoNumero> faixasNossoNumero) {
        this.faixasNossoNumero = faixasNossoNumero;
    }

    @Transient
    public boolean precisaCarregarUnidade() {
        return unidade == null || !unidade.getId().equals(idUnidade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConvenioBancario convenioBancario)) return false;
        return Objects.equals(getId(), convenioBancario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
