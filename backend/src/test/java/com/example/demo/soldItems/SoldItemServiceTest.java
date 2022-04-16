package com.example.demo.soldItems;

import com.example.demo.categories.Category;
import com.example.demo.categories.CategoryRepository;
import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashData;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoldItemServiceTest {

    @Test
    @DisplayName("should create item")
    void test1() {

        SoldItemRepository soldItemRepository = mock(SoldItemRepository.class);
        HelloCashService helloCashService = mock(HelloCashService.class);
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        HelloCashItem item1 = new HelloCashItem();
        HelloCashItem item2 = new HelloCashItem();
        item1.setItemName("blume");
        item1.setItemPrice("1.0");
        item1.setItemQuantity("1.000");
        item2.setItemName("topf");
        item2.setItemPrice("2.0");
        item2.setItemQuantity("2.000");
        List<HelloCashItem> itemList1 = List.of(item1, item2);
        List<HelloCashItem> itemList2 = List.of(item1, item2);

        HelloCashInvoice invoice1 = new HelloCashInvoice();
        HelloCashInvoice invoice2 = new HelloCashInvoice();

        invoice1.setItems(itemList1);
        invoice1.setInvoiceNumber("1");
        invoice1.setInvoiceTimestamp("22 33");

        invoice2.setItems(itemList2);
        invoice2.setInvoiceNumber("2");
        invoice2.setInvoiceTimestamp("23 34");

        List<HelloCashInvoice> invoiceList1 = List.of(invoice1, invoice2);
        List<HelloCashInvoice> invoiceList2 = List.of(invoice1, invoice2);
        HelloCashData helloCashData1 = new HelloCashData();
        helloCashData1.setInvoices(invoiceList1);
        helloCashData1.setCount("100");
        helloCashData1.setLimit(5);
        helloCashData1.setOffset(1);
        HelloCashData helloCashData2 = new HelloCashData();
        helloCashData2.setInvoices(invoiceList2);
        helloCashData2.setCount("50");
        helloCashData2.setLimit(10);
        helloCashData2.setOffset(1);

        List<HelloCashData> helloCashDataList = List.of(helloCashData1, helloCashData2);

        List<SoldItem> expectedItems = List.of(
                new SoldItem(null, "22", "33", "1", "blume", 1.0, 1.000),
                new SoldItem(null, "22", "33", "1", "topf", 2.0, 2.000),
                new SoldItem(null, "23", "34", "2", "blume", 1.0, 1.000),
                new SoldItem(null, "23", "34", "2", "topf", 2.0, 2.000)
        );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dateTo = dtf.format(now);

        UserData userDataToSave = new UserData("1", "Steve", "12345", dateTo);

        when(userRepository.save(userDataToSave)).thenReturn(userDataToSave);

        when(userRepository.getLastUpdateByUsername("Steve"))
                .thenReturn(new UserData("1", "Steve", "12345", "2022-03-03"));

        when(helloCashService.getInvoicesFromHelloCashApi(eq("2022-03-03"), any()))
                .thenReturn(helloCashDataList.stream());

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);
        soldItemService.saveSoldItems("Steve");

        verify(userRepository).save(userDataToSave);
        verify(soldItemRepository, Mockito.times(2)).saveAll(expectedItems);

    }

    @Test
    @DisplayName("should not save Data to Database")
    void test2() {

        SoldItemRepository soldItemRepository = mock(SoldItemRepository.class);
        HelloCashService helloCashService = mock(HelloCashService.class);
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dateFrom = dtf.format(now);

        when(userRepository.getLastUpdateByUsername("Steve"))
                .thenReturn(new UserData("1", "Steve", "12345", dateFrom));

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);
        soldItemService.saveSoldItems("Steve");

        verifyNoInteractions(soldItemRepository);

    }

    @Test
    @DisplayName("verify that soldItemRepository.findAll() is called")
    void test3() {
        SoldItemRepository soldItemRepository = mock(SoldItemRepository.class);
        HelloCashService helloCashService = mock(HelloCashService.class);
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);

        soldItemService.getAllItemNames();

        verify(soldItemRepository).findAll();

    }

    @Test
    @DisplayName("should return a valid result")
    void test4() {
        SoldItemRepository soldItemRepository = mock(SoldItemRepository.class);
        HelloCashService helloCashService = mock(HelloCashService.class);
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        DataForQuery dataForQuery = new DataForQuery();
        dataForQuery.setSearchTherm("1");
        dataForQuery.setDateFrom("2022-01-01");
        dataForQuery.setDateTo("2022-01-02");

        List<String> dates = List.of("2022-01-01", "2022-01-02");
        List<String> artikelList = List.of("artikel1", "artikel2");

        Category category = new Category();
        category.setId("1");
        category.setItemsInCategory(artikelList);

        SoldItem item1 = new SoldItem();
        SoldItem item2 = new SoldItem();

        item1.setItemName("artikel1");
        item1.setItemPrice(10);
        item1.setItemQuantity(1);
        item2.setItemName("artikel2");
        item2.setItemPrice(15);
        item2.setItemQuantity(1);

        when(categoryRepository.findById(dataForQuery.getSearchTherm())).thenReturn(Optional.of(category));

        when(soldItemRepository.findAllByInvoiceDateIn(dates)).thenReturn(List.of(item1, item2));

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);
        Result actual = soldItemService.getResults(dataForQuery);

        assertThat(actual.getSumOfAllItems()).isEqualTo(25);
        assertThat(actual.getSoldItems().size()).isEqualTo(2);
        assertThat(actual.getSoldItems().get(0).getItemPrice()).isEqualTo(10);

    }

    @Test
    @DisplayName("should return a valid result")
    void test5(){

        SoldItemRepository soldItemRepository = mock(SoldItemRepository.class);
        HelloCashService helloCashService = mock(HelloCashService.class);
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        DataForQuery query = new DataForQuery();
        query.setSearchTherm("strauß");
        query.setDateFrom("2022-01-01");
        query.setDateTo("2022-01-03");

        SoldItem item1 = new SoldItem();
        SoldItem item2 = new SoldItem();

        item1.setId("1");
        item1.setInvoiceNumber("1");
        item1.setItemName("strauß");
        item1.setItemPrice(10.0);
        item1.setItemQuantity(2.0);
        item1.setInvoiceDate("2022-01-01");
        item1.setInvoiceTime("5");

        item2.setId("2");
        item2.setInvoiceNumber("1");
        item2.setItemPrice(15.0);
        item2.setItemName("strauß");
        item2.setItemQuantity(3.0);
        item2.setInvoiceDate("2022-01-01");
        item2.setInvoiceTime("3");

        List<SoldItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        List<String> listOfDates = List.of("2022-01-01", "2022-01-02", "2022-01-03");

        when(soldItemRepository.findAllByInvoiceDateIn(listOfDates)).thenReturn(items);

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);

        List<DataForItemChart> actual = soldItemService.getDataForItemChart(query);

        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual.get(0).getDate()).isEqualTo("2022-01-01");
        assertThat(actual.get(2).getDate()).isEqualTo("2022-01-03");
        assertThat(actual.get(0).getQuantity()).isEqualTo(5);
        assertThat(actual.get(0).getSales()).isEqualTo(65);

    }

    @Test
    @DisplayName("haha")
    void test6() {

        SoldItemRepository soldItemRepository = mock(SoldItemRepository.class);
        HelloCashService helloCashService = mock(HelloCashService.class);
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        DataForQuery query = new DataForQuery();
        query.setSearchTherm("kategorie");
        query.setDateFrom("2022-01-01");
        query.setDateTo("2022-01-03");

        Category category = new Category("1","kategorie", List.of("strauß","blume"));

        SoldItem item1 = new SoldItem("","2022-01-01","","","strauß",10.00,1);
        SoldItem item2 = new SoldItem("","2022-01-01","","","strauß",10.00,1);
        SoldItem item3 = new SoldItem("","2022-01-02","","","blume",15.00,1);
        SoldItem item4 = new SoldItem("","2022-01-02","","","blume",15.00,1);

        List<SoldItem> itemList = List.of(item1, item2, item3, item4);

        when(categoryRepository.findById(query.getSearchTherm())).thenReturn(Optional.of(category));
        when(soldItemRepository.findAllByInvoiceDateIn(List.of("2022-01-01","2022-01-02","2022-01-03"))).thenReturn(itemList);

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);

        List<DataLineChartCategory> actual = soldItemService.getDataLineChartCategory(query);

        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual.get(0).getSales()).isEqualTo(20);
        assertThat(actual.get(2).getSales()).isEqualTo(0);

    }

}