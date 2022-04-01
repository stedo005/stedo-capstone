package com.example.demo.soldItems;

import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoldItemService {

    private final SoldItemRepository soldItemRepository;
    private final HelloCashService helloCashService;
    private final UserRepository userRepository;

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

    public List<String> getAllNames() {
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

}
