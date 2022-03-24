package com.example.coinprofit.telegram.command;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.FlowStatus;
import com.example.coinprofit.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class DepositCommand implements Command {

    private static final String DEPOSIT_MESSAGE = "There are 3 ways to set up deposit:\n\n" +
            "1. Send csv file with deposit details in this format:\n" +
            "name;count;price\n" +
            "ethereum-classic;5;2900.3\n" +
            "near-protocol;2;11.2\n" +
            "...\n\n" +
            "2. Send json file in this format:\n" +
            " [{\n" +
            "    \"name\": \"ethereum-classic\",\n" +
            "    \"count\": \"5\",\n" +
            "    \"price\": \"2900.3\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"near-protocol\",\n" +
            "    \"count\": \"2\",\n" +
            "    \"price\": \"11.2\"\n" +
            "  }\n" +
            "...\n]\n\n" +
            "3. Text message with deposit detail in this format:\n" +
            "ethereum-classic 5 2900.3\n" +
            "near-protocol 2 11.2\n" +
            "...\n\n" +
            "Cryptocurrency name you can find in the url on this site:\n" +
            "https://coinmarketcap.com/";

    private final AccountService accountService;
    @Override
    public String getResponseMessage(Message message) {
        AccountDocument account = accountService.getAccountFromMessage(message);
        if (FlowStatus.DEPOSIT != account.getFlowStatus()) {
            account.setFlowStatus(FlowStatus.DEPOSIT);
            accountService.save(account);
        }
        return DEPOSIT_MESSAGE;
    }
}
