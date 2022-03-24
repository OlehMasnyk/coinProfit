package com.example.coinprofit.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfitDto {

    private List<CoinProfitDto> details = new ArrayList<>();
    private Double amount = 0.0;
}
