package com.example.demo.helloCash;

import com.example.demo.helloCash.dataModel.HelloCashData;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HelloCashService {

    private final SoldItemRepository soldItemRepository;

    @Value("${hello-cash.username}") String username;
    @Value("${hello-cash.password}") String password;

    public void addSoldItem() {

        String url = "https://api.hellocash.business/api/v1/invoices?limit=5&offset=1&search=&dateFrom=&dateTo=&mode=&showDetails=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        String json = response.getBody();
        HelloCashData helloCashData = new Gson().fromJson(json, HelloCashData.class);

        for(HelloCashInvoice invoice : helloCashData.getInvoices()) {
            for(HelloCashItem item : invoice.getItems()) {
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
