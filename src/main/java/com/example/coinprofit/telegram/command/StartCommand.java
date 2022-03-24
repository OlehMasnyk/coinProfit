package com.example.coinprofit.telegram.command;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.FlowStatus;
import com.example.coinprofit.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private static final String START_MESSAGE = "Welcome to your bot, that calculates profit from your cryptocurrency deposits.\n\n" +
            "To set up deposit details, use this command: /deposit\n" +
            "To see your profit, use this commandL /profit";

    private final AccountService accountService;

    @Override
    public String getResponseMessage(Message message) {
        AccountDocument account = accountService.getAccountFromMessage(message);
        if (FlowStatus.MAIN != account.getFlowStatus()) {
            account.setFlowStatus(FlowStatus.MAIN);
            accountService.save(account);
        }
        return START_MESSAGE;
    }
}
