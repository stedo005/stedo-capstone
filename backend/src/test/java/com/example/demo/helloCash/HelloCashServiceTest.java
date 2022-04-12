package com.example.demo.helloCash;

import com.example.demo.helloCash.dataModel.HelloCashData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HelloCashServiceTest {

    @Test
    @DisplayName("test that API Result is less then 1000")
    void test1 (){

        String dateFrom = "2022-01-01";
        String dateTo = "2022-02-02";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("username", "password");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        String body = "{\n" +
                "    \"invoices\": [\n" +
                "        {\n" +
                "            \"invoice_id\": \"74235333\",\n" +
                "            \"invoice_timestamp\": \"2022-03-30 14:44:58\",\n" +
                "            \"invoice_number\": \"22002851\",\n" +
                "            \"invoice_cashier\": \"Mitarbeiter\",\n" +
                "            \"invoice_cashier_id\": \"103836\",\n" +
                "            \"invoice_mode\": \"default\",\n" +
                "            \"invoice_payment\": \"Bar\",\n" +
                "            \"invoice_total\": \"9.00\",\n" +
                "            \"invoice_totalNet\": \"8.41\",\n" +
                "            \"invoice_totalTax\": \"0.59\",\n" +
                "            \"invoice_currency\": \"EUR\",\n" +
                "            \"invoice_cancellation\": \"0\",\n" +
                "            \"company\": {\n" +
                "                \"name\": \"P\",\n" +
                "                \"street\": \"B\",\n" +
                "                \"houseNumber\": \"7\",\n" +
                "                \"postalCode\": \"9\",\n" +
                "                \"city\": \"K\",\n" +
                "                \"email\": \"i\",\n" +
                "                \"website\": \"w\",\n" +
                "                \"phoneNumber\": \"0\",\n" +
                "                \"companyRegister\": \"D\",\n" +
                "                \"iban\": \"D\",\n" +
                "                \"bic\": \"H\"\n" +
                "            },\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"item_id\": \"146288968\",\n" +
                "                    \"item_quantity\": \"3.000\",\n" +
                "                    \"item_name\": \"Pflanze\",\n" +
                "                    \"item_price\": \"3\",\n" +
                "                    \"item_total\": \"9\",\n" +
                "                    \"item_taxRate\": \"7\",\n" +
                "                    \"item_discount\": \"-0\",\n" +
                "                    \"item_discount_unit\": \"percent\",\n" +
                "                    \"item_discount_value\": \"0\",\n" +
                "                    \"item_service_id\": \"0\",\n" +
                "                    \"item_article_id\": \"4598507\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"taxes\": [\n" +
                "                {\n" +
                "                    \"tax_taxRate\": \"7\",\n" +
                "                    \"tax_gross\": \"9\",\n" +
                "                    \"tax_net\": \"8.41\",\n" +
                "                    \"tax_tax\": \"0.59\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"invoice_id\": \"74234529\",\n" +
                "            \"invoice_timestamp\": \"2022-03-30 14:35:42\",\n" +
                "            \"invoice_number\": \"22002850\",\n" +
                "            \"invoice_cashier\": \"Mitarbeiter\",\n" +
                "            \"invoice_cashier_id\": \"103836\",\n" +
                "            \"invoice_mode\": \"default\",\n" +
                "            \"invoice_payment\": \"Bankomat\",\n" +
                "            \"invoice_total\": \"29.00\",\n" +
                "            \"invoice_totalNet\": \"27.10\",\n" +
                "            \"invoice_totalTax\": \"1.9\",\n" +
                "            \"invoice_currency\": \"EUR\",\n" +
                "            \"invoice_cancellation\": \"0\",\n" +
                "            \"company\": {\n" +
                "                \"name\": \"P\",\n" +
                "                \"street\": \"B\",\n" +
                "                \"houseNumber\": \"7\",\n" +
                "                \"postalCode\": \"9\",\n" +
                "                \"city\": \"K\",\n" +
                "                \"email\": \"i\",\n" +
                "                \"website\": \"w\",\n" +
                "                \"phoneNumber\": \"0\",\n" +
                "                \"companyRegister\": \"D\",\n" +
                "                \"iban\": \"D\",\n" +
                "                \"bic\": \"H\"\n" +
                "            },\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"item_id\": \"146287419\",\n" +
                "                    \"item_quantity\": \"1.000\",\n" +
                "                    \"item_name\": \"Blumenstrauß\",\n" +
                "                    \"item_price\": \"29\",\n" +
                "                    \"item_total\": \"29\",\n" +
                "                    \"item_taxRate\": \"7\",\n" +
                "                    \"item_discount\": \"-0\",\n" +
                "                    \"item_discount_unit\": \"percent\",\n" +
                "                    \"item_discount_value\": \"0\",\n" +
                "                    \"item_service_id\": \"0\",\n" +
                "                    \"item_article_id\": \"4585965\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"taxes\": [\n" +
                "                {\n" +
                "                    \"tax_taxRate\": \"7\",\n" +
                "                    \"tax_gross\": \"29\",\n" +
                "                    \"tax_net\": \"27.1\",\n" +
                "                    \"tax_tax\": \"1.9\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"count\": \"2\",\n" +
                "    \"limit\": 2,\n" +
                "    \"offset\": 20\n" +
                "}";

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
                ))
                .thenReturn(ResponseEntity.ok(body));

        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=&search=&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        HelloCashService helloCashService = new HelloCashService(restTemplate, "username", "password");

        List<HelloCashData> actual = helloCashService.getInvoicesFromHelloCashApi(dateFrom, dateTo).toList();

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getInvoices()).hasSize(2);
        assertThat(actual.get(0).getInvoices().get(0).getItems()).hasSize(1);
        assertThat(actual.get(0).getInvoices().get(1).getItems()).hasSize(1);

    }

    @Test
    @DisplayName("test that API Result is more then 1000")
    void test2 (){

        String dateFrom = "2022-01-01";
        String dateTo = "2022-02-02";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("username", "password");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        String body = "{\n" +
                "    \"invoices\": [\n" +
                "        {\n" +
                "            \"invoice_id\": \"74235333\",\n" +
                "            \"invoice_timestamp\": \"2022-03-30 14:44:58\",\n" +
                "            \"invoice_number\": \"22002851\",\n" +
                "            \"invoice_cashier\": \"Mitarbeiter\",\n" +
                "            \"invoice_cashier_id\": \"103836\",\n" +
                "            \"invoice_mode\": \"default\",\n" +
                "            \"invoice_payment\": \"Bar\",\n" +
                "            \"invoice_total\": \"9.00\",\n" +
                "            \"invoice_totalNet\": \"8.41\",\n" +
                "            \"invoice_totalTax\": \"0.59\",\n" +
                "            \"invoice_currency\": \"EUR\",\n" +
                "            \"invoice_cancellation\": \"0\",\n" +
                "            \"company\": {\n" +
                "                \"name\": \"P\",\n" +
                "                \"street\": \"B\",\n" +
                "                \"houseNumber\": \"3\",\n" +
                "                \"postalCode\": \"9\",\n" +
                "                \"city\": \"K\",\n" +
                "                \"email\": \"i\",\n" +
                "                \"website\": \"w\",\n" +
                "                \"phoneNumber\": \"0\",\n" +
                "                \"companyRegister\": \"D\",\n" +
                "                \"iban\": \"D\",\n" +
                "                \"bic\": \"H\"\n" +
                "            },\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"item_id\": \"146288968\",\n" +
                "                    \"item_quantity\": \"3.000\",\n" +
                "                    \"item_name\": \"Pflanze\",\n" +
                "                    \"item_price\": \"3\",\n" +
                "                    \"item_total\": \"9\",\n" +
                "                    \"item_taxRate\": \"7\",\n" +
                "                    \"item_discount\": \"-0\",\n" +
                "                    \"item_discount_unit\": \"percent\",\n" +
                "                    \"item_discount_value\": \"0\",\n" +
                "                    \"item_service_id\": \"0\",\n" +
                "                    \"item_article_id\": \"4598507\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"taxes\": [\n" +
                "                {\n" +
                "                    \"tax_taxRate\": \"7\",\n" +
                "                    \"tax_gross\": \"9\",\n" +
                "                    \"tax_net\": \"8.41\",\n" +
                "                    \"tax_tax\": \"0.59\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"invoice_id\": \"74234529\",\n" +
                "            \"invoice_timestamp\": \"2022-03-30 14:35:42\",\n" +
                "            \"invoice_number\": \"22002850\",\n" +
                "            \"invoice_cashier\": \"Mitarbeiter\",\n" +
                "            \"invoice_cashier_id\": \"103836\",\n" +
                "            \"invoice_mode\": \"default\",\n" +
                "            \"invoice_payment\": \"Bankomat\",\n" +
                "            \"invoice_total\": \"29.00\",\n" +
                "            \"invoice_totalNet\": \"27.10\",\n" +
                "            \"invoice_totalTax\": \"1.9\",\n" +
                "            \"invoice_currency\": \"EUR\",\n" +
                "            \"invoice_cancellation\": \"0\",\n" +
                "            \"company\": {\n" +
                "                \"name\": \"P\",\n" +
                "                \"street\": \"B\",\n" +
                "                \"houseNumber\": \"8\",\n" +
                "                \"postalCode\": \"9\",\n" +
                "                \"city\": \"K\",\n" +
                "                \"email\": \"i\",\n" +
                "                \"website\": \"w\",\n" +
                "                \"phoneNumber\": \"0\",\n" +
                "                \"companyRegister\": \"D\",\n" +
                "                \"iban\": \"D\",\n" +
                "                \"bic\": \"H\"\n" +
                "            },\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"item_id\": \"146287419\",\n" +
                "                    \"item_quantity\": \"1.000\",\n" +
                "                    \"item_name\": \"Blumenstrauß\",\n" +
                "                    \"item_price\": \"29\",\n" +
                "                    \"item_total\": \"29\",\n" +
                "                    \"item_taxRate\": \"7\",\n" +
                "                    \"item_discount\": \"-0\",\n" +
                "                    \"item_discount_unit\": \"percent\",\n" +
                "                    \"item_discount_value\": \"0\",\n" +
                "                    \"item_service_id\": \"0\",\n" +
                "                    \"item_article_id\": \"4585965\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"taxes\": [\n" +
                "                {\n" +
                "                    \"tax_taxRate\": \"7\",\n" +
                "                    \"tax_gross\": \"29\",\n" +
                "                    \"tax_net\": \"27.1\",\n" +
                "                    \"tax_tax\": \"1.9\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"count\": \"3000\",\n" +
                "    \"limit\": 2,\n" +
                "    \"offset\": 20\n" +
                "}";

        RestTemplate restTemplate = mock(RestTemplate.class);

        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1&offset=&search=&dateFrom=" + dateFrom +
                        "&dateTo=" + dateTo +
                        "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=4&search=&dateFrom=" + dateFrom +
                        "&dateTo=" + dateTo +
                        "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=3&search=&dateFrom=" + dateFrom +
                        "&dateTo=" + dateTo +
                        "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=2&search=&dateFrom=" + dateFrom +
                        "&dateTo=" + dateTo +
                        "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        when(restTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=1&search=&dateFrom=" + dateFrom +
                        "&dateTo=" + dateTo +
                        "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        HelloCashService helloCashService = new HelloCashService(restTemplate, "username", "password");

        List<HelloCashData> actual = helloCashService.getInvoicesFromHelloCashApi(dateFrom, dateTo).toList();

        assertThat(actual).hasSize(3);
        assertThat(actual.get(0).getInvoices()).hasSize(2);
        assertThat(actual.get(0).getInvoices().get(0).getItems()).hasSize(1);
        assertThat(actual.get(0).getInvoices().get(1).getItems()).hasSize(1);

        assertThat(actual.get(2).getInvoices()).hasSize(2);
        assertThat(actual.get(2).getInvoices().get(0).getItems()).hasSize(1);
        assertThat(actual.get(2).getInvoices().get(1).getItems()).hasSize(1);

    }

}