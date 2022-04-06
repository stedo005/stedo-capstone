package com.example.demo.soldItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private double sumOfAllItems;
    private List<SoldItem> soldItems;

    public double getSumOfAllItems() {
        return soldItems.stream()
                .mapToDouble(e -> e.getTotalPrice())
                .sum();
    }
}
