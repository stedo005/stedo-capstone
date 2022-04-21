package com.example.demo;

import com.example.demo.categories.Category;
import com.example.demo.security.LoginData;
import com.example.demo.soldItems.evaluateCategory.EvaluateCategoryDTO;
import com.example.demo.user.UserData;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BudgetCheckerApplicationTests {

    @Autowired
    private TestRestTemplate endpointsMyApi;

    @Autowired
    MongoTemplate mongoTemplate;

    @MockBean
    private RestTemplate mockTemplate;

    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Test
    void contextLoad() {

    }

    @Test
    @DisplayName("Integration Test")
    void test () {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        String dateFrom = dtf.format(now.minusDays(3L));
        String dateTo = dtf.format(now.minusDays(1L));
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("username", "password");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        String body = "{\n" +
                "    \"invoices\": [\n" +
                "        {\n" +
                "            \"invoice_id\": \"74235333\",\n" +
                "            \"invoice_timestamp\": \"" + dtf.format(now.minusDays(3L)) + " 14:44:58\",\n" +
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
                "            \"invoice_timestamp\": \"" + dtf.format(now.minusDays(2L)) + " 14:35:42\",\n" +
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

        when(mockTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1&offset=&search=&dateFrom="
                        + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        when(mockTemplate.exchange(
                "https://api.hellocash.business/api/v1/invoices?limit=1000&offset=&search=&dateFrom="
                        + dateFrom + "&dateTo=" + dateTo + "&mode=&showDetails=true",
                HttpMethod.GET,
                request,
                String.class
        ))
                .thenReturn(ResponseEntity.ok(body));

        String encodePwd = passwordEncoder().encode("12345");
        DBObject user = BasicDBObjectBuilder.start()
                .add("id", "1")
                .add("username", "Steve")
                .add("password", encodePwd)
                .add("lastUpdate", dateFrom)
                .get();

        DBObject user1 = BasicDBObjectBuilder.start()
                .add("id", "2")
                .add("username", "Bernd")
                .add("password", encodePwd)
                .add("lastUpdate", dtf.format(now))
                .get();

        mongoTemplate.save(user, "users");
        mongoTemplate.save(user1, "users");

        LoginData loginData = new LoginData("Steve", "12345");
        UserData userData = new UserData(null, "Steve", "12345", "2022-01-01");

        ResponseEntity<Void> createUser = endpointsMyApi.postForEntity("/api/users", userData, Void.class);
        assertThat(createUser.getStatusCode()).isEqualTo(HttpStatus.valueOf(403));

        ResponseEntity<String> login = endpointsMyApi.postForEntity("/api/login", loginData, String.class);
        assertThat(login.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = login.getBody();

        HttpHeaders bearerAuthHeader = new HttpHeaders();
        assert token != null;
        bearerAuthHeader.setBearerAuth(token);

        ResponseEntity<UserData> getUser = endpointsMyApi.exchange("/api/users/Steve", HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), UserData.class);
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(getUser.getBody()).getUsername()).isEqualTo("Steve");

        ResponseEntity<String> getData = endpointsMyApi.exchange("/api/getData/Bernd", HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), String.class);
        assertThat(getData.getStatusCode()).isEqualTo(HttpStatus.valueOf(304));

        ResponseEntity<String> getData1 = endpointsMyApi.exchange("/api/getData/Steve", HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), String.class);
        assertThat(getData1.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));

        ResponseEntity<String[]> allItemNames = endpointsMyApi.exchange("/api/soldItems", HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), String[].class);
        assertThat(allItemNames.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(allItemNames.getBody()).length).isEqualTo(2);
        assertThat(Objects.requireNonNull(allItemNames.getBody())[0]).isEqualTo("Blumenstrauß");

        Category category = new Category(null, "Blumen", List.of("Blumenstrauß"));
        ResponseEntity<Category> createCategory = endpointsMyApi.exchange("/api/category", HttpMethod.POST, new HttpEntity<>(category, bearerAuthHeader), Category.class);
        assertThat(createCategory.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(createCategory.getBody()).getCategoryName()).isEqualTo("Blumen");
        assertThat(Objects.requireNonNull(createCategory.getBody()).getItemsInCategory().get(0)).isEqualTo("Blumenstrauß");

        String categoryId = createCategory.getBody().getId();

        Category changedCategory = new Category(categoryId, "Blumen", List.of("Blumenstrauß", "Ampeln"));
        ResponseEntity<Category> addItemsToCategory = endpointsMyApi.exchange("/api/category", HttpMethod.PUT, new HttpEntity<>(changedCategory, bearerAuthHeader), Category.class);

        ResponseEntity<Category[]> getCategories = endpointsMyApi.exchange("/api/category", HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), Category[].class);
        assertThat(getCategories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(getCategories.getBody()).length).isEqualTo(1);
        assertThat(getCategories.getBody()[0].getItemsInCategory().get(1)).isEqualTo("Ampeln");

        ResponseEntity<Category> getItemsInCategory = endpointsMyApi.exchange("/api/category/" + categoryId, HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), Category.class);
        assertThat(getItemsInCategory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(getItemsInCategory.getBody()).getItemsInCategory().size()).isEqualTo(2);

        Category changeCategoryAgain = new Category(categoryId, "Blumen", List.of("Blumenstrauß", "Pflanze"));
        ResponseEntity<Category> changeItemsInCategory = endpointsMyApi.exchange("/api/category", HttpMethod.PUT, new HttpEntity<>(changeCategoryAgain, bearerAuthHeader), Category.class);

        ResponseEntity<Category> getItemsInCategory1 = endpointsMyApi.exchange("/api/category/" + categoryId, HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), Category.class);
        assertThat(getItemsInCategory1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(getItemsInCategory1.getBody()).getItemsInCategory().size()).isEqualTo(2);
        assertThat(getItemsInCategory1.getBody().getItemsInCategory().get(1)).isEqualTo("Pflanze");


        ResponseEntity<EvaluateCategoryDTO> getEvaluatedData = endpointsMyApi.exchange("/api/soldItems/evaluateCategory?searchTerm=" + categoryId + "&dateFrom=" + dateFrom + "&dateTo=" + dateTo, HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), EvaluateCategoryDTO.class);
        assertThat(getEvaluatedData.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(getEvaluatedData.getBody()).getSumOfAllItems()).isEqualTo(38);
        assertThat(Objects.requireNonNull(getEvaluatedData.getBody()).getQuantities().get(0).getItem()).isEqualTo("Pflanze");
        assertThat(Objects.requireNonNull(getEvaluatedData.getBody()).getQuantities().get(0).getQuantity()).isEqualTo(3);
        assertThat(getEvaluatedData.getBody().getSales().size()).isEqualTo(3);
        assertThat(getEvaluatedData.getBody().getSales().get(1).getDate()).isEqualTo(dtf.format(now.minusDays(2L)));
        assertThat(getEvaluatedData.getBody().getSales().get(1).getSales()).isEqualTo(29);

        ResponseEntity<Void> deleteCategory = endpointsMyApi.exchange("/api/category/" + categoryId, HttpMethod.DELETE, new HttpEntity<>(bearerAuthHeader), Void.class);
        assertThat(deleteCategory.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        ResponseEntity<Category[]> getCategoriesAfterDelete = endpointsMyApi.exchange("/api/category", HttpMethod.GET, new HttpEntity<>(bearerAuthHeader), Category[].class);
        assertThat(getCategoriesAfterDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(getCategoriesAfterDelete.getBody()).length).isEqualTo(0);

    }

}
