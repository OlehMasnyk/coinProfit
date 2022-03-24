package com.example.coinprofit.telegram.job;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.dto.ProfitDto;
import com.example.coinprofit.service.AccountService;
import com.example.coinprofit.service.CoinDepositService;
import com.example.coinprofit.telegram.CoinDepositBot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScheduledProfitStatusJob {
    private final AccountService accountService;
    private final CoinDepositService databaseCoinDepositService;
    private final CoinDepositBot coinDepositBot;

    // Run EVERY HOUR BETWEEN 7 AND 21
    @Scheduled(cron = "0 0 7-21 * * *")
    public void sendProfitStatus() {
        List<AccountDocument> accounts = accountService.getAllWithDepositDetail();

        accounts
                .forEach(account -> {
                    if (!account.getCoinDeposits().isEmpty()) {
                        Map<String, Double> coinsPrice = databaseCoinDepositService.getCoinsPrice(account.getCoinDeposits());
                        ProfitDto profitDto = databaseCoinDepositService.calculateProfit(account, coinsPrice);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(account.getChatId());
                        sendMessage.setText(databaseCoinDepositService.getProfitText(profitDto, coinsPrice));
                        try {
                            coinDepositBot.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
