package com.example.demo.soldItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "soldItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldItem {

    @Id
    private String id;
    private String invoiceDate;
    private String invoiceTime;
    private String invoiceNumber;
    private String itemName;
    private double itemPrice;
    private double itemQuantity;

    public double getTotalPrice () {

        return itemQuantity * itemPrice;

    }

}
