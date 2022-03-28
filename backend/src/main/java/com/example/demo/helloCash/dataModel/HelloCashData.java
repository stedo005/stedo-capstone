package com.example.demo.helloCash.dataModel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HelloCashData {

    private List<HelloCashInvoice> invoices = new ArrayList<>();
    private String count;
    private Integer offset;

}
