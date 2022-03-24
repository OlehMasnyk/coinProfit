package com.example.coinprofit.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CoinDeposit {

    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "count")
    private Double count;
    @CsvBindByName(column = "price")
    private Double price;
}
