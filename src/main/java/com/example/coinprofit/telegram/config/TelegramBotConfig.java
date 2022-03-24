package com.example.coinprofit.telegram.config;

import com.example.coinprofit.telegram.CoinDepositBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class TelegramBotConfig {

    private final CoinDepositBot coinDepositBot;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(coinDepositBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
