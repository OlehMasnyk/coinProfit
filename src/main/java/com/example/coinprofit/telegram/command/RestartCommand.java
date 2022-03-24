package com.example.coinprofit.telegram.command;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.FlowStatus;
import com.example.coinprofit.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class RestartCommand implements Command {

    private static final String RESTART_MESSAGE = "Your deposit details successfully cleared.\n\n" +
            "To set up deposit details, use this command: /deposit\n" +
            "To see your profit, use this commandL /profit";

    private final AccountService accountService;

    @Override
    public String getResponseMessage(Message message) {
        AccountDocument account = accountService.getAccountFromMessage(message);
        account.setCoinDeposits(Collections.emptyList());
        account.setFlowStatus(FlowStatus.MAIN);
        accountService.save(account);
        return RESTART_MESSAGE;
    }
}
