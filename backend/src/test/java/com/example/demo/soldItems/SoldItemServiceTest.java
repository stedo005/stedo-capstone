package com.example.demo.soldItems;

import com.example.demo.categories.CategoryRepository;
import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashData;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        invoice1.setInvoiceTimestamp("22");

        invoice2.setItems(itemList2);
        invoice2.setInvoiceNumber("2");
        invoice2.setInvoiceTimestamp("23");

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
                new SoldItem(null, "22", "1", "blume", "1.0", "1.000"),
                new SoldItem(null, "22", "1", "topf", "2.0", "2.000"),
                new SoldItem(null, "23", "2", "blume", "1.0", "1.000"),
                new SoldItem(null, "23", "2", "topf", "2.0", "2.000"),
                new SoldItem(null, "22", "1", "blume", "1.0", "1.000"),
                new SoldItem(null, "22", "1", "topf", "2.0", "2.000"),
                new SoldItem(null, "23", "2", "blume", "1.0", "1.000"),
                new SoldItem(null, "23", "2", "topf", "2.0", "2.000")
        );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dateTo = dtf.format(now);

        UserData userDataToSave = new UserData("1", "Steve", "12345", dateTo);

        when(userRepository.save(userDataToSave)).thenReturn(userDataToSave);

        when(userRepository.getLastUpdateByUsername("Steve"))
                .thenReturn(new UserData("1", "Steve", "12345", "2022-03-03"));

        when(helloCashService.getInvoicesFromHelloCashApi(eq("2022-03-03"), any()))
                .thenReturn(helloCashDataList);

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository, categoryRepository);
        soldItemService.saveSoldItems("Steve");

        verify(userRepository).save(userDataToSave);
        verify(soldItemRepository).saveAll(expectedItems);

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

}