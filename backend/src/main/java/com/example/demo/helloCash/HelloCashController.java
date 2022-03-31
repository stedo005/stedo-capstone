package com.example.demo.helloCash;

import com.example.demo.soldItems.SoldItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getData")
@RequiredArgsConstructor
public class HelloCashController {

    private final SoldItemService soldItemService;

    @GetMapping("/{username}")
    public ResponseEntity<String> getDataFromHelloCash(@PathVariable String username) {

        return soldItemService.saveSoldItems(username);

    }

}
