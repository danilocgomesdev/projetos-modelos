package fieg.modulos.protheus.dto;


import fieg.core.exceptions.NegocioException;
import fieg.modulos.cr5.model.CobrancasClientes;

import java.io.Serializable;

public class ParcelaProteusDTO implements Serializable {

    private Integer numeroParcela;
    private String cbcSituacao;
    private Integer recno;

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getCbcSituacao() {
        return cbcSituacao;
    }

    public void setCbcSituacao(String cbcSituacao) {

        if(cbcSituacao != null){
            if(!CobrancasClientes.PROVISORIO.equals(cbcSituacao)
                    && !CobrancasClientes.EM_ABERTO.equals(cbcSituacao)
                    && !CobrancasClientes.AGRUPADO.equals(cbcSituacao)){
                String msg = String.format("O campo {parcelasProteus.cbcSituacao} permite apenas os valores: %s, %s e %s"
                        ,CobrancasClientes.PROVISORIO
                        ,CobrancasClientes.EM_ABERTO
                        ,CobrancasClientes.AGRUPADO
                );
                throw new NegocioException(msg);
            }
        }
        this.cbcSituacao = cbcSituacao;
    }

    public Integer getRecno() {
        return recno;
    }

    public void setRecno(Integer recno) {
        this.recno = recno;
    }
}
