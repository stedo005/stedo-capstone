package com.example.demo.serverToServer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "selledItem")
@Data
@NoArgsConstructor
public class StSModell {

    @Id
    private String id;
    private String invoiceNumber;

}
