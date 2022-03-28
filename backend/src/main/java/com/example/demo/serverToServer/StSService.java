package com.example.demo.serverToServer;

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
public class StSService {

    private final StSRepository stSRepository;

    @Value("${hello-cash.username}") String username;
    @Value("${hello-cash.password}") String password;

    public void addSelledItem() {

        String url = "https://api.hellocash.business/api/v1/invoices?limit=5&offset=1&search=&dateFrom=&dateTo=&mode=&showDetails=true";

        HttpHeaders headers = new HttpHeaders();
        headers.add(username, password);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        String json = response.getBody();
        HelloCashData helloCashData = new Gson().fromJson(json, HelloCashData.class);

        StSModell model = new StSModell();
        model.setInvoiceNumber(helloCashData.getInvoices().get(0).getInvoiceNumber());

        stSRepository.save(model);

    }

}
