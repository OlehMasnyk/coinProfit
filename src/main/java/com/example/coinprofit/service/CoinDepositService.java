package com.example.coinprofit.service;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.CoinDepositDocument;
import com.example.coinprofit.dto.ProfitDto;

import java.util.List;
import java.util.Map;

public interface CoinDepositService {
    Map<String, Double> getCoinsPrice(List<CoinDepositDocument> coinDeposits);

    ProfitDto calculateProfit(AccountDocument accountDocument, Map<String, Double> coinPrices);

    String getProfitText(ProfitDto profitDto, Map<String, Double> coinsPrice);
}
