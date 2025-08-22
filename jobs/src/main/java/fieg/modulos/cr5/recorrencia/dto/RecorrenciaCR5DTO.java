package fieg.modulos.cr5.recorrencia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RecorrenciaCR5DTO {

    private Integer id;

    private Integer entidade;

    private String idRecorrencia;

    private String statusRecorrencia;

    private Date inicioData;

    private Date fimData;



}
