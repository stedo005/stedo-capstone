package com.example.demo.serverToServer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getData")
@RequiredArgsConstructor
public class StSController {

    private final StSService stSService;

    @GetMapping
    public void getDataFromHelloCash() {

        stSService.addSelledItem();

    }

}
