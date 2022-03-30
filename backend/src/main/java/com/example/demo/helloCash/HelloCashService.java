package com.example.demo.helloCash;

import com.example.demo.helloCash.dataModel.HelloCashData;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import com.example.demo.soldItems.SoldItem;
import com.example.demo.soldItems.SoldItemRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class HelloCashService {

    private final SoldItemRepository soldItemRepository;
    private final UserRepository userRepository;

    @Value("${hello-cash.username}")
    String username;
    @Value("${hello-cash.password}")
    String password;

    public String addSoldItem(String name) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        UserData userData = userRepository.getLastUpdateByUsername(name);

        String dateFrom = userData.getLastUpdate();
        String dateTo = dtf.format(now);

        userData.setLastUpdate(dateTo);
        userRepository.save(userData);

        if (dateFrom.equals(dateTo)) {

            return "Daten sind aktuell!";

        } else {

            dateTo = dtf.format(now.minusDays(1L));

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> responseCheckCount = new RestTemplate()
                    .exchange(
                            "https://api.hellocash.business/api/v1/invoices?limit=1&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                            HttpMethod.GET,
                            request,
                            String.class
                    );
            String jsonCheckCount = responseCheckCount.getBody();
            HelloCashData dataCheckCount = new Gson().fromJson(jsonCheckCount, HelloCashData.class);

            int count = Integer.parseInt(dataCheckCount.getCount());

            if (count > 1000) {
                int offset = (count / 1000) + 1;

                for (int i = offset; i >= 1; i--) {
                    ResponseEntity<String> responseForDatabase = new RestTemplate()
                            .exchange(
                                    "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=" + i + "&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                                    HttpMethod.GET,
                                    request,
                                    String.class
                            );
                    makeSoldItems(responseForDatabase);
                }
            } else {
                ResponseEntity<String> responseForDatabase = new RestTemplate()
                        .exchange(
                                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                                HttpMethod.GET,
                                request,
                                String.class
                        );
                makeSoldItems(responseForDatabase);
            }
            return "Daten wurden aktualisiert!";
        }
    }

    private void makeSoldItems(ResponseEntity<String> responseForDatabase) {
        String jsonForDatabase = responseForDatabase.getBody();
        HelloCashData dataForDatabase = new Gson().fromJson(jsonForDatabase, HelloCashData.class);

        for (HelloCashInvoice invoice : dataForDatabase.getInvoices()) {
            for (HelloCashItem item : invoice.getItems()) {
                SoldItem soldItem = new SoldItem();
                soldItem.setItemName(item.getItemName());
                soldItem.setItemPrice(item.getItemPrice());
                soldItem.setItemQuantity(item.getItemQuantity());
                soldItem.setInvoiceTimestamp(invoice.getInvoiceTimestamp());
                soldItem.setInvoiceNumber(invoice.getInvoiceNumber());
                soldItemRepository.save(soldItem);
            }
        }
    }
}
