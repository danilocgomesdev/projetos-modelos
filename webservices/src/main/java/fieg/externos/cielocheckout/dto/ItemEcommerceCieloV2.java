package fieg.externos.cielocheckout.dto;

import lombok.Data;

@Data
public class ItemEcommerceCieloV2 {
    private String name;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
