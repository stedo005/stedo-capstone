package com.example.demo.helloCash;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HelloCashData {

    private List<HelloCashInvoice> invoices = new ArrayList<>();

}
