package com.example.demo.soldItems;

import com.example.demo.categories.Category;
import com.example.demo.categories.CategoryRepository;
import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

            List<SoldItem> soldItems = helloCashService.getInvoicesFromHelloCashApi(dateFrom, dateTo)
                    .stream()
                    .flatMap(helloCashData -> helloCashData.getInvoices().stream())
                    .flatMap(invoice -> invoice.getItems().stream().map(item -> makeSoldItem(item, invoice)))
                    .toList();
            soldItemRepository.saveAll(soldItems);
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
        soldItem.setItemPrice(item.getItemPrice());
        soldItem.setItemQuantity(item.getItemQuantity());
        soldItem.setInvoiceTimestamp(invoice.getInvoiceTimestamp());
        soldItem.setInvoiceNumber(invoice.getInvoiceNumber());

        return soldItem;

    }

    public void getItemByDate(String categoryId, String dateFrom, String dateTo) {

        List<String> itemsInCategory;

        if (categoryRepository.findById(categoryId).isPresent()) {
            itemsInCategory = categoryRepository.findById(categoryId).get().getItemsInCategory();
        } else {
            throw new IllegalArgumentException("Kategorie existiert nicht!");
        }

        LocalDate dateStart = LocalDate.parse(dateFrom);
        LocalDate dateStop = LocalDate.parse(dateTo);

        List<List<SoldItem>> listOfDatesWithItemsInDateRange = new ArrayList<>();
        List<String> dateRangeToGet = new ArrayList<>();

        dateRangeToGet.add(dateStart.toString());
        while (!dateStart.equals(dateStop)) {
            dateRangeToGet.add(dateStart.plusDays(1L).toString());
            dateStart = (dateStart.plusDays(1L));
        }

        for (int i = 0; i < dateRangeToGet.size(); i++) {
            listOfDatesWithItemsInDateRange.add(soldItemRepository.findAllByInvoiceTimestampContains(dateRangeToGet.get(i)));
        }

        List<List<SoldItem>> allItems = new ArrayList<>();

        for (int i = 0; i < listOfDatesWithItemsInDateRange.size(); i++) {
            List<SoldItem> listOfItemInCurrentDate = listOfDatesWithItemsInDateRange.get(i);
            for (int j = 0; j <itemsInCategory.size(); j++) {
                int finalJ = j;
                allItems.add(listOfItemInCurrentDate.stream()
                        .filter(e -> e.getItemName().equals(itemsInCategory.get(finalJ))).toList());
            }
        }

        List<SoldItem> soldItems = allItems.stream().flatMap(e -> e.stream()).toList();

        System.out.println(soldItems);

    }


}
