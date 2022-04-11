package com.example.demo.soldItems;

import com.example.demo.categories.CategoryRepository;
import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        result.setSoldItems(getItemsByQueryData(dates));

        return result;

    }

    private List<SoldItem> getItemsByQueryData(DataForQuery dates) {

        List<String> itemsInCategory = categoryRepository.findById(dates.getCategoryId())
                .map(category -> category.getItemsInCategory())
                .orElseThrow(() -> new IllegalArgumentException("Kategorie existiert nicht!"));

        LocalDate dateStart = LocalDate.parse(dates.getDateFrom());
        LocalDate dateStop = LocalDate.parse(dates.getDateTo());

        List<String> dateRangeToGet = new ArrayList<>();

        dateRangeToGet.add(dateStart.toString());
        while (!dateStart.equals(dateStop)) {
            dateRangeToGet.add(dateStart.plusDays(1L).toString());
            dateStart = (dateStart.plusDays(1L));
        }

        return soldItemRepository.findAllByInvoiceDateIn(dateRangeToGet).stream()
                .filter(soldItem -> itemsInCategory.contains(soldItem.getItemName()))
                .toList();

    }


}
