package com.example.demo.soldItems;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryItemChart {

    private String currentItem;
    private String dateFrom;
    private String dateTo;

}
