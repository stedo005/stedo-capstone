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

    @GetMapping("/query")
    public EvaluateCategoryDTO getDataLineChartCategory(@RequestParam(name = "searchTerm") String searchTerm, @RequestParam(name = "dateFrom") String dateFrom, @RequestParam(name = "dateTo") String dateTo) {
        DataForQuery dataForQuery = new DataForQuery();
        dataForQuery.setSearchTerm(searchTerm);
        dataForQuery.setDateFrom(dateFrom);
        dataForQuery.setDateTo(dateTo);
        return soldItemService.getDataLineChartCategory(dataForQuery);
    }

}
