package com.example.demo.helloCash;

import com.example.demo.helloCash.dataModel.HelloCashData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloCashService {

    private final RestTemplate restTemplate;

    private final String username;
    private final String password;

    public HelloCashService(RestTemplate restTemplate, @Value("${hello-cash.username}") String username, @Value("${hello-cash.password}") String password) {
        this.restTemplate = restTemplate;
        this.username = username;
        this.password = password;
    }

    public List<HelloCashData> getInvoicesFromHelloCashApi(String dateFrom, String dateTo) {

        List<HelloCashData> soldItems = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseCheckCount = restTemplate
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
            int offset = ((count-1) / 1000) + 1;

            for (int i = offset; i >= 1; i--) {
                ResponseEntity<String> responseForDatabase = restTemplate
                        .exchange(
                                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=" + i + "&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                                HttpMethod.GET,
                                request,
                                String.class
                        );
                HelloCashData helloCashData = new Gson().fromJson(responseForDatabase.getBody(), HelloCashData.class);
                soldItems.add(helloCashData);
            }
        } else {
            ResponseEntity<String> responseForDatabase = restTemplate
                    .exchange(
                            "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                            HttpMethod.GET,
                            request,
                            String.class
                    );
            HelloCashData helloCashData = new Gson().fromJson(responseForDatabase.getBody(), HelloCashData.class);
            soldItems.add(helloCashData);
        }
        return soldItems;

    }

}
