package com.example.demo.lastUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lastUpdate")
@Data
@NoArgsConstructor
public class LastUpdate {

    @Id
    private String id;
    private String username;
    private String timestamp;

}
