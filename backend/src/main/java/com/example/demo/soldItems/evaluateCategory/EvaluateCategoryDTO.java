package com.example.demo.soldItems.evaluateCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateCategoryDTO {

    private List<DateSales> sales;
    private List<ItemQuantity> quantities;
    private double sumOfAllItems;

}
