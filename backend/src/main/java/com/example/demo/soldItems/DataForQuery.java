package com.example.demo.soldItems;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataForQuery {

    private String categoryId;
    private String dateFrom;
    private String dateTo;

}
