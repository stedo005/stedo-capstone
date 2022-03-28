package com.example.demo.serverToServer;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HelloCashInvoice {

    @SerializedName("invoice_number")
    private String invoiceNumber;

}
