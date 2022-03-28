package com.example.demo.helloCash;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "soldItem")
@Data
@NoArgsConstructor
public class SoldItem {

    @Id
    private String id;
    private String invoiceTimestamp;
    private String invoiceNumber;
    private String itemName;
    private String itemPrice;
    private String itemQuantity;

}
