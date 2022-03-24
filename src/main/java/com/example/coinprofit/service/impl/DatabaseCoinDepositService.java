package com.example.coinprofit.service.impl;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.CoinDepositDocument;
import com.example.coinprofit.dto.CoinProfitDto;
import com.example.coinprofit.dto.ProfitDto;
import com.example.coinprofit.mapper.CoinDepositMapper;
import com.example.coinprofit.service.CoinDepositService;
import com.example.coinprofit.service.CoinValueReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class DatabaseCoinDepositService implements CoinDepositService {

    private final ExecutorService coinPriceExecutors;
    private final CoinValueReader coinValueReader;
    private final CoinDepositMapper coinDepositMapper;

    @Override
    public Map<String, Double> getCoinsPrice(List<CoinDepositDocument> coinDeposits) {
        Map<String, Future<Double>> coinPricesTask = new ConcurrentHashMap<>();
        Map<String, Double> coinPrices = new HashMap<>();
        for (CoinDepositDocument coinDeposit : coinDeposits) {
            coinPricesTask.putIfAbsent(coinDeposit.getName(), coinPriceExecutors.submit(() -> coinValueReader.getPrice(coinDeposit.getName())));
        }
        for (Map.Entry<String, Future<Double>> pair : coinPricesTask.entrySet()) {
            try {
                coinPrices.put(pair.getKey(), pair.getValue().get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                coinPrices.put(pair.getKey(), Double.NaN);
            }
        }
        return coinPrices;
    }

    @Override
    public ProfitDto calculateProfit(AccountDocument accountDocument, Map<String, Double> coinPrices) {
        ProfitDto profitDto = new ProfitDto();
        for (CoinDepositDocument coinDeposit : accountDocument.getCoinDeposits()) {
            Double coinPrice = coinPrices.get(coinDeposit.getName());
            if (!coinPrice.isNaN()) {
                CoinProfitDto coinProfit = coinDepositMapper.toDto(coinDeposit, coinPrice);
                profitDto.getDetails().add(coinProfit);
                profitDto.setAmount(coinProfit.getProfit() + profitDto.getAmount());
            }
        }
        return profitDto;
    }

    @Override
    public String getProfitText(ProfitDto profitDto, Map<String, Double> coinsPrice) {
        double totalInvestSum = profitDto.getDetails()
                .stream()
                .mapToDouble(detail -> detail.getBuyPrice() * detail.getCount())
                .sum();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Total profit: %1$,.2f USD\nTotal Invested: %2$,.2f USD\n\nDetails:\n", profitDto.getAmount(), totalInvestSum));
        for (CoinProfitDto detail : profitDto.getDetails()) {
            builder.append(String.format("%s (%2$,.2f) profit: %3$,.2f USD\n", detail.getCoin().toUpperCase(), detail.getBuyPrice() * detail.getCount(), detail.getProfit()));
        }
        builder.append("\n\n");
        for (Map.Entry<String, Double> pair : coinsPrice.entrySet()) {
            if (pair.getValue().isNaN()) {
                builder.append("Not found price for ").append(pair.getKey()).append(". Please check correct coin name.");
            }
        }
        builder.append("/profit");
        return builder.toString();
    }
}
