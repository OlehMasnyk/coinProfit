package com.example.coinprofit.telegram.command;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.CoinDepositDocument;
import com.example.coinprofit.dto.ProfitDto;
import com.example.coinprofit.service.AccountService;
import com.example.coinprofit.service.CoinDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProfitCommand implements Command {
    private final AccountService accountService;
    private final CoinDepositService databaseCoinDepositService;

    @Override
    public String getResponseMessage(Message message) {
        AccountDocument account = accountService.getAccountFromMessage(message);
        List<CoinDepositDocument> coinDeposits = account.getCoinDeposits();
        if (coinDeposits == null || coinDeposits.isEmpty()) {
            return "Total profit: 0.0 USD\n\n No deposit information.";
        }
        Map<String, Double> coinsPrice = databaseCoinDepositService.getCoinsPrice(coinDeposits);
        ProfitDto profitDto = databaseCoinDepositService.calculateProfit(account, coinsPrice);

        return databaseCoinDepositService.getProfitText(profitDto, coinsPrice);
    }


}
