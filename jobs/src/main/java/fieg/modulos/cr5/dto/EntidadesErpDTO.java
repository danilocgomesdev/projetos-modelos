package fieg.modulos.cr5.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntidadesErpDTO implements Serializable {

    private String filialErp ;

    private Integer ano;


    @Override
    public String toString() {
        return "EntidadesErpDTO{" +
                "filialErp='" + filialErp + '\'' +
                ", ano=" + ano +
                '}';
    }
}
