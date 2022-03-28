package com.example.demo.helloCash;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "selledItem")
@Data
@NoArgsConstructor
public class SoldItem {

    @Id
    private String id;
    private String invoiceNumber;

}
