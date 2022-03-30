package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BudgetCheckerApplicationTests {

    @Value("${hello-cash.username}")
    String username;
    @Value("${hello-cash.password}")
    String password;

}
