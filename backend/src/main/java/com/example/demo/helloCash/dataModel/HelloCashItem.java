package com.example.demo.helloCash.dataModel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HelloCashItem {

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("item_price")
    private String itemPrice;

    @SerializedName("item_quantity")
    private String itemQuantity;

}
