package com.example.demo;

import com.example.demo.security.LoginData;
import com.example.demo.user.UserData;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BudgetCheckerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Test
    void contextLoad() {

    }

    @Test
    @DisplayName("Integration Test")
    void test () {

        String encodePwd = passwordEncoder().encode("12345");

        DBObject user = BasicDBObjectBuilder.start()
                .add("id", "1")
                .add("username", "Steve")
                .add("password", encodePwd)
                .add("lastUpdate", "2022-01-01")
                .get();

        mongoTemplate.save(user, "users");

        LoginData loginData = new LoginData("Steve", "12345");
        UserData userData = new UserData(null, "Steve", "12345", "2022-01-01");

        ResponseEntity<Void> createUser = restTemplate.postForEntity("/api/users", userData, Void.class);
        assertThat(createUser.getStatusCode()).isEqualTo(HttpStatus.valueOf(403));

        ResponseEntity<String> login = restTemplate.postForEntity("/api/login", loginData, String.class);
        assertThat(login.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = login.getBody();



    }

}
