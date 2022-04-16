package com.example.demo.soldItems;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataForQuery {

    private String searchTerm;
    private String dateFrom;
    private String dateTo;

}
