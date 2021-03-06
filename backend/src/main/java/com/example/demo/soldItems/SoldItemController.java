package com.example.demo.soldItems;

import com.example.demo.soldItems.evaluateCategory.EvaluateCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/itemChart")
    public List<DataForItemChart> getDataForItemChart(@RequestBody DataForQuery query){
        return soldItemService.getDataForItemChart(query);
    }

    @GetMapping("/evaluateCategory")
    public ResponseEntity<EvaluateCategoryDTO> getDataLineChartCategory(@RequestParam(name = "searchTerm") String searchTerm, @RequestParam(name = "dateFrom") String dateFrom, @RequestParam(name = "dateTo") String dateTo) {
        DataForQuery dataForQuery = new DataForQuery();
        dataForQuery.setSearchTerm(searchTerm);
        dataForQuery.setDateFrom(dateFrom);
        dataForQuery.setDateTo(dateTo);
        return ResponseEntity.ok(soldItemService.getDataLineChartCategory(dataForQuery));
    }

}
