package fieg.modulos.unidade.model;

import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.entidade.enums.EntidadeIntConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "CR5_UNIDADES")
public class UnidadeCR5 {

    @Id
    @Column(name = "ID_UNIDADE")
    private Integer id;

    @Column(name = "ENTIDADE")
    private Integer codigoEntidade;

    // Usar a coluna de id para atualizações/inserir!!
    @Column(name = "ENTIDADE", insertable = false, updatable = false)
    @Convert(converter = EntidadeIntConverter.class)
    private Entidade entidade;

    @Column(name = "COD_UNIDADE", length = 5)
    private String codigoUnidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoEntidade() {
        return codigoEntidade;
    }

    public void setCodigoEntidade(Integer codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public String getCodigoUnidade() {
        return codigoUnidade;
    }

    public void setCodigoUnidade(String codigoUnidade) {
        this.codigoUnidade = codigoUnidade;
    }
}
