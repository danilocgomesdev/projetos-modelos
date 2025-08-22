package fieg.modulos.cr5.recorrencia.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemEcommerceCieloV2 {
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
