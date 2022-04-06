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
    public List<SoldItem> getItemsByQueryData(@RequestBody DataForQuery dates) {
        return soldItemService.getItemByQueryData(dates.getCategoryId(), dates.getDateFrom(), dates.getDateTo());
    }

}
