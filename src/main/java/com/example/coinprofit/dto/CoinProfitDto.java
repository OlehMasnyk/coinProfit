package com.example.coinprofit.dto;

import lombok.Data;

@Data
public class CoinProfitDto {
    private String coin;
    private Double profit;
    private Double count;
    private Double buyPrice;
    private Double currentPrice;
}
