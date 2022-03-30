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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelloCashService {


    @Value("${hello-cash.username}")
    String username;
    @Value("${hello-cash.password}")
    String password;

    public List<HelloCashData> getInvoicesfromHelloCashApi(String dateFrom, String dateTo) {

        List<HelloCashData> soldItems = new ArrayList<>();

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
                    HelloCashData helloCashData = new Gson().fromJson(jsonCheckCount, HelloCashData.class);
                    soldItems.add(helloCashData);
                }
            } else {
                ResponseEntity<String> responseForDatabase = new RestTemplate()
                        .exchange(
                                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                                HttpMethod.GET,
                                request,
                                String.class
                        );
                HelloCashData helloCashData = new Gson().fromJson(jsonCheckCount, HelloCashData.class);
                soldItems.add(helloCashData);
            }
            return soldItems;

    }

}
