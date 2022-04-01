package com.example.demo.soldItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoldItemDTO {

    private String itemName;
    private String itemPrice;
    private String itemQuantity;

    public static SoldItemDTO of(SoldItem soldItem) {
        return new SoldItemDTO(soldItem.getItemName(), soldItem.getItemPrice(), soldItem.getItemQuantity());
    }

}
