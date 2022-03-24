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
public class NotificationCommand implements Command {

    private static final String ENABLE_MESSAGE = "Notification is ON";
    private static final String DISABLE_MESSAGE = "Notification is OFF";

    private final AccountService accountService;

    @Override
    public String getResponseMessage(Message message) {
        AccountDocument account = accountService.getAccountFromMessage(message);
        boolean notification = !account.getNotification();
        account.setNotification(notification);
        account.setFlowStatus(FlowStatus.MAIN);
        accountService.save(account);
        return notification ? ENABLE_MESSAGE : DISABLE_MESSAGE;
    }
}
