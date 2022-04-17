package com.example.demo.soldItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateCategoryDTO {

    private List<DataLineChartCategory> chartData;
    private double sumOfAllItems;

}
