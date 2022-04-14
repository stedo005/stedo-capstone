package com.example.demo.soldItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForItemChart {

    private String date;
    private double sales;
    private double quantity;

}
