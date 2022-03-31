package com.example.demo.helloCash.dataModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HelloCashInvoice {

    @SerializedName("invoice_timestamp")
    private String invoiceTimestamp;

    @SerializedName("invoice_number")
    private String invoiceNumber;

    private List<HelloCashItem> items = new ArrayList<>();

}
