package com.example.demo.soldItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateCategoryDTO {

    private List<DataLineChartCategory> chartData;
    private Map<String, Double> quantityPerItem;
    private double sumOfAllItems;

}
