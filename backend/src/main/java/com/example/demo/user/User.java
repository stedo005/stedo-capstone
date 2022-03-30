package com.example.demo.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lastUpdate")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String lastUpdate;

}
