package com.example.demo.soldItems;

import com.example.demo.categories.CategoryRepository;
import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.soldItems.evaluateCategory.DateSales;
import com.example.demo.soldItems.evaluateCategory.EvaluateCategoryDTO;
import com.example.demo.soldItems.evaluateCategory.ItemQuantity;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SoldItemService {

    private final SoldItemRepository soldItemRepository;
    private final HelloCashService helloCashService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<String> saveSoldItems(String name) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        LocalDateTime now = LocalDateTime.now();

        UserData userData = userRepository.getLastUpdateByUsername(name);

        String dateFrom = userData.getLastUpdate();
        String dateTo = dtf.format(now);

        userData.setLastUpdate(dateTo);
        userRepository.save(userData);

        if (!dateFrom.equals(dateTo)) {

            dateTo = dtf.format(now.minusDays(1L));

            helloCashService.getInvoicesFromHelloCashApi(dateFrom, dateTo)
                    .forEach(helloCashData -> {
                        List<SoldItem> soldItems = helloCashData.getInvoices().stream()
                                .flatMap(invoice -> invoice.getItems().stream().map(item -> makeSoldItem(item, invoice)))
                                .toList();
                        soldItemRepository.saveAll(soldItems);
                    });

            return ResponseEntity.status(201).body("Database refreshed");

        }
        return ResponseEntity.status(304).body("nothing to refresh");
    }

    public List<String> getAllItemNames() {
        return soldItemRepository.findAll().stream()
                .map(soldItem -> soldItem.getItemName())
                .distinct()
                .sorted()
                .toList();
    }

    private SoldItem makeSoldItem(HelloCashItem item, HelloCashInvoice invoice) {

        SoldItem soldItem = new SoldItem();
        soldItem.setItemName(item.getItemName());
        soldItem.setItemPrice(Double.parseDouble(item.getItemPrice()));
        soldItem.setItemQuantity(Double.parseDouble(item.getItemQuantity()));
        soldItem.setInvoiceDate(invoice.getInvoiceTimestamp().split(" ")[0]);
        soldItem.setInvoiceTime(invoice.getInvoiceTimestamp().split(" ")[1]);
        soldItem.setInvoiceNumber(invoice.getInvoiceNumber());

        return soldItem;

    }

    public Result getResults(DataForQuery dates) {

        Result result = new Result();
        result.setSoldItems(getItemsByDateList(dates));

        return result;

    }

    public List<DataForItemChart> getDataForItemChart (DataForQuery query) {

        List<DataForItemChart> dataForItemCharts = new ArrayList<>();
        List<String> dates = getDateList(query);
        List<SoldItem> soldItems = soldItemRepository.findAllByInvoiceDateIn(dates).stream()
                .filter(soldItem -> query.getSearchTerm().equals(soldItem.getItemName()))
                .toList();
        for (String date: dates) {
            DataForItemChart currentData = new DataForItemChart();
            List<SoldItem> currentList = soldItems.stream()
                    .filter(soldItem -> date.equals(soldItem.getInvoiceDate()))
                    .toList();
            currentData.setSales(currentList.stream()
                    .mapToDouble(value -> value.getTotalPrice())
                    .sum());
            currentData.setQuantity(currentList.stream()
                    .mapToDouble(value -> value.getItemQuantity())
                    .sum());
            currentData.setDate(date);
            dataForItemCharts.add(currentData);
        }

        return dataForItemCharts;
    }

    public EvaluateCategoryDTO getDataLineChartCategory(DataForQuery dataForQuery) {

        EvaluateCategoryDTO evaluateCategoryDTO = new EvaluateCategoryDTO();
        List<DateSales> dateSales = new ArrayList<>();

        List<String> dateList = getDateList(dataForQuery);
        List<SoldItem> allItemsInDateList = getItemsByDateList(dataForQuery);
        evaluateCategoryDTO.setSumOfAllItems(
                allItemsInDateList.stream()
                        .mapToDouble(value -> value.getTotalPrice())
                        .sum());

        evaluateCategoryDTO.setQuantities(getQuantityPerItem(dataForQuery));

        for(String date: dateList) {
            DateSales currentData = new DateSales();
            List<SoldItem> currentItems = allItemsInDateList.stream()
                    .filter(soldItem -> date.equals(soldItem.getInvoiceDate()))
                    .toList();
            currentData.setSales(currentItems.stream()
                    .mapToDouble(value -> value.getTotalPrice())
                    .sum());
            currentData.setDate(date);
            dateSales.add(currentData);
        }

        evaluateCategoryDTO.setSales(dateSales);

        return evaluateCategoryDTO;
    }

    private List<ItemQuantity> getQuantityPerItem(DataForQuery dataForQuery) {

        List<SoldItem> soldItemList = getItemsByDateList(dataForQuery);
        List<ItemQuantity> quantities = new ArrayList<>();

        for(SoldItem item: soldItemList) {
            ItemQuantity quantity = new ItemQuantity();
            quantity.setQuantity(soldItemList.stream()
                    .filter(soldItem -> soldItem.getItemName().equals(item.getItemName()))
                    .mapToDouble(value -> value.getItemQuantity())
                    .sum());
            quantity.setItem(item.getItemName());
            quantities.add(quantity);
        }

        return quantities.stream()
                .distinct()
                .toList();

    }

    private List<SoldItem> getItemsByDateList(DataForQuery dataForQuery) {

        List<String> itemsInCategory = categoryRepository.findById(dataForQuery.getSearchTerm())
                .map(category -> category.getItemsInCategory())
                .orElseThrow(() -> new IllegalArgumentException("Kategorie existiert nicht!"));

        List<String> dates = getDateList(dataForQuery);

        return soldItemRepository.findAllByInvoiceDateIn(dates).stream()
                .filter(soldItem -> itemsInCategory.contains(soldItem.getItemName()))
                .toList();

    }

    private List<String> getDateList(DataForQuery query){

        LocalDate dateStart = LocalDate.parse(query.getDateFrom());
        LocalDate dateStop = LocalDate.parse(query.getDateTo());

        List<String> dates = new ArrayList<>();
        dates.add(dateStart.toString());

        while (!dateStart.equals(dateStop)) {
            dates.add(dateStart.plusDays(1L).toString());
            dateStart = (dateStart.plusDays(1L));
        }

        return dates;

    }


}
