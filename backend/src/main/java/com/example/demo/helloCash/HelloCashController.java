package com.example.demo.helloCash;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getData")
@RequiredArgsConstructor
public class HelloCashController {

    private final HelloCashService stSService;

    @GetMapping
    public void getDataFromHelloCash() {

        stSService.addSelledItem();

    }

}
