package com.example.demo.serverToServer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HelloCashData {

    private List<HelloCashInvoice> invoices = new ArrayList<>();

}
