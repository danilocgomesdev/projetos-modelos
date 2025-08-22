package fieg.modulos.cr5.dto;

import java.io.Serializable;
import java.util.List;

public class ParcelasCieloDTO implements Serializable {

    private List<Integer> idTransacaoParc;
    private String meioUtilizado;

    public ParcelasCieloDTO(){
    }

    public List<Integer> getIdTransacaoParc() {
        return idTransacaoParc;
    }

    public void setIdTransacaoParc(List<Integer> idTransacaoParc) {
        this.idTransacaoParc = idTransacaoParc;
    }

    public String getMeioUtilizado() {
        return meioUtilizado;
    }

    public void setMeioUtilizado(String meioUtilizado) {
        this.meioUtilizado = meioUtilizado;
    }
}
