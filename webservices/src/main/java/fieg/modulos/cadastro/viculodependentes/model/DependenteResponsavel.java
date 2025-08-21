package fieg.modulos.cadastro.viculodependentes.model;


import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import jakarta.persistence.*;

@Entity
@Table(name = "CR5_DEPENDENTE_RESPONSAVEL")
public class DependenteResponsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPENDENTE_RESPONSAVEL")
    private Integer idDependenteResponsavel;

    @ManyToOne
    @JoinColumn(name = "ID_DEPENDENTE", nullable = false)
    private Dependente dependente;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOARESPONSAVEL", nullable = false)
    private PessoaCr5 pessoaResponsavel;

    public Integer getIdDependenteResponsavel() {
        return idDependenteResponsavel;
    }

    public void setIdDependenteResponsavel(Integer idDependenteResponsavel) {
        this.idDependenteResponsavel = idDependenteResponsavel;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    public PessoaCr5 getPessoaResponsavel() {
        return pessoaResponsavel;
    }

    public void setPessoaResponsavel(PessoaCr5 pessoaResponsavel) {
        this.pessoaResponsavel = pessoaResponsavel;
    }
}

