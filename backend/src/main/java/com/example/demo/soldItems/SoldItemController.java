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
    public Result getResults(@RequestBody DataForQuery query) {
        return soldItemService.getResults(query);
    }

    @PutMapping("/itemChart")
    public List<DataForItemChart> getDataForItemChart(@RequestBody DataForQuery query){
        return soldItemService.getDataForItemChart(query);
    }

}
