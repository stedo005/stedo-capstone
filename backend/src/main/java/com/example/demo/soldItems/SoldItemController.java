package com.example.demo.soldItems;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soldItems")
@RequiredArgsConstructor
public class SoldItemController {

    private final SoldItemService soldItemService;

    @GetMapping
    public List<String> getAllItemNames () {
        return soldItemService.getAllItemNames();
    }

    @PutMapping
    public Result getResults(@RequestBody DataForQuery dates) {
        return soldItemService.getResults(dates);
    }

    @PutMapping
    public void getDataForItemChart(@RequestBody QueryItemChart query){

    }

}
