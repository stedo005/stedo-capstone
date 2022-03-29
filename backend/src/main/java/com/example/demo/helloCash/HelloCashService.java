package com.example.demo.helloCash;

import com.example.demo.helloCash.dataModel.HelloCashData;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.lastUpdate.LastUpdate;
import com.example.demo.lastUpdate.LastUpdateRepository;
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
    private final LastUpdateRepository lastUpdateRepository;

    @Value("${hello-cash.username}") String username;
    @Value("${hello-cash.password}") String password;

    public void addSoldItem(String name) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        LastUpdate lastUpdate = lastUpdateRepository.getLastUpdateByUsername(name);
        String dateFrom = lastUpdate.getTimestamp();
        String dateTo = dtf.format(now);
        if(dateTo.equals(dateFrom)){
            dateFrom = dtf.format(now.minusDays(1L));
            dateTo = dtf.format(now.minusDays(1L));
        }
        lastUpdate.setTimestamp(dtf.format(now));
        lastUpdateRepository.save(lastUpdate);

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

        if(count > 1000) {
            int offset = (count/1000) + 1;

            for (int i = offset; i >= 1 ; i--) {
                ResponseEntity<String> responseForDatabase = new RestTemplate()
                        .exchange(
                                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=" + i + "&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                                HttpMethod.GET,
                                request,
                                String.class
                        );
                String jsonForDatabase = responseForDatabase.getBody();
                HelloCashData dataForDatabase = new Gson().fromJson(jsonForDatabase, HelloCashData.class);

                for(HelloCashInvoice invoice : dataForDatabase.getInvoices()) {
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
        } else {
            ResponseEntity<String> responseForDatabase = new RestTemplate()
                    .exchange(
                            "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                            HttpMethod.GET,
                            request,
                            String.class
                    );
            String jsonForDatabase = responseForDatabase.getBody();
            HelloCashData dataForDatabase = new Gson().fromJson(jsonForDatabase, HelloCashData.class);

            for(HelloCashInvoice invoice : dataForDatabase.getInvoices()) {
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
}
