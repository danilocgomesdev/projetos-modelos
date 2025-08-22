package fieg.modulos.protheus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ParcelasDTO {

    public Integer parcela;
    public Integer idInterface;
    public String dataVencimento;
    public BigDecimal valor;
}
