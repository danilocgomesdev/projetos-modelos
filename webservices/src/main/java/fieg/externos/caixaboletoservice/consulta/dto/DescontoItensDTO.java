package fieg.externos.caixaboletoservice.consulta.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescontoItensDTO {

    private String data;
    private String valor;

    private String percentual;
}
