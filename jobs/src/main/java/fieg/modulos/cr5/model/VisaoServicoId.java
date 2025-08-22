package fieg.modulos.cr5.model;

import java.io.Serializable;
import java.util.Objects;

public class VisaoServicoId implements Serializable {

    private Integer idProduto;
    private Integer idSistema;

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        VisaoServicoId that = (VisaoServicoId) object;
        return Objects.equals(idProduto, that.idProduto) && Objects.equals(idSistema, that.idSistema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, idSistema);
    }
}
