package fieg.modulos.cadastro.conveniobancario.model;

import fieg.core.enums.AtivoInativoConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_CONVENIOSBANCARIOSFAIXANN")
public class FaixaNossoNumero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONVENIOS", nullable = false)
    private Integer id;

    @Column(name = "FNN_SITUACAO", nullable = false)
    @Convert(converter = AtivoInativoConverter.class)
    private Boolean ativo;

    @Column(name = "FNN_NOSSONUMEROINICIAL", columnDefinition = "varchar(20)", length = 20, nullable = false)
    private String nossoNumeroInicial;

    @Column(name = "FNN_NOSSONUMEROFINAL", columnDefinition = "varchar(20)", length = 20, nullable = false)
    private String nossoNumeroFinal;

    @Column(name = "FNN_NOSSONUMEROATUAL", columnDefinition = "varchar(20)", length = 20, nullable = false)
    private String nossoNumeroAtual;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    // TODO candidata forte a ser dropada
    @Column(name = "FNN_NROCORRENCIA")
    private Integer numeroRecorrencia;

    @ManyToOne
    @JoinColumn(name = "ID_CONVENIOSBANCARIOS")
    private ConvenioBancario convenioBancario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getNossoNumeroInicial() {
        return nossoNumeroInicial;
    }

    public void setNossoNumeroInicial(String nossoNumeroInicial) {
        this.nossoNumeroInicial = nossoNumeroInicial;
    }

    public String getNossoNumeroFinal() {
        return nossoNumeroFinal;
    }

    public void setNossoNumeroFinal(String nossoNumeroFinal) {
        this.nossoNumeroFinal = nossoNumeroFinal;
    }

    public String getNossoNumeroAtual() {
        return nossoNumeroAtual;
    }

    public void setNossoNumeroAtual(String nossoNumeroAtual) {
        this.nossoNumeroAtual = nossoNumeroAtual;
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

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public Integer getNumeroRecorrencia() {
        return numeroRecorrencia;
    }

    public void setNumeroRecorrencia(Integer numeroRecorrencia) {
        this.numeroRecorrencia = numeroRecorrencia;
    }

    public ConvenioBancario getConvenioBancario() {
        return convenioBancario;
    }

    public void setConvenioBancario(ConvenioBancario convenioBancario) {
        this.convenioBancario = convenioBancario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaixaNossoNumero faixa)) return false;
        return Objects.equals(getId(), faixa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
