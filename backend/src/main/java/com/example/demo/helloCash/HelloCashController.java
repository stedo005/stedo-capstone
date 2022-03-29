package com.example.demo.helloCash;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getData")
@RequiredArgsConstructor
public class HelloCashController {

    private final HelloCashService helloCashService;

    @GetMapping("/{username}")
    public String getDataFromHelloCash(@PathVariable String username) {

        return helloCashService.addSoldItem(username);

    }

}
