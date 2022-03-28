package com.example.demo.helloCash.dataModel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HelloCashInvoice {

    @SerializedName("invoice_number")
    private String invoiceNumber;

}
