package com.example.demo.soldItems;

import com.example.demo.helloCash.HelloCashService;
import com.example.demo.helloCash.dataModel.HelloCashData;
import com.example.demo.helloCash.dataModel.HelloCashInvoice;
import com.example.demo.helloCash.dataModel.HelloCashItem;
import com.example.demo.user.UserData;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class SoldItemServiceTest {

    @Test
    @DisplayName("should create item")
    void test1() {

        SoldItemRepository soldItemRepository = Mockito.mock(SoldItemRepository.class);
        HelloCashService helloCashService = Mockito.mock(HelloCashService.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

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

        List<HelloCashData> helloCashDataList = List.of(helloCashData1, helloCashData2);

        Mockito.when(userRepository.getLastUpdateByUsername("Steve"))
                .thenReturn(new UserData("1","Steve","12345","2022-03-03"));

        Mockito.when(helloCashService.getInvoicesFromHelloCashApi(Mockito.eq("2022-03-03"), Mockito.any()))
                .thenReturn(helloCashDataList);

        SoldItemService soldItemService = new SoldItemService(soldItemRepository, helloCashService, userRepository);
        soldItemService.saveSoldItems("Steve");

        Mockito.verify(soldItemRepository).saveAll(expectedItems);

    }

}