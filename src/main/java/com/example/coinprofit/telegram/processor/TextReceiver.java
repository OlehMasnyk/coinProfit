package com.example.coinprofit.telegram.processor;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.FlowStatus;
import com.example.coinprofit.model.CoinDeposit;
import com.example.coinprofit.parser.impl.TextCoinDepositParser;
import com.example.coinprofit.service.AccountService;
import com.example.coinprofit.telegram.exception.MessageProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TextReceiver {

    private final AccountService accountService;
    private final TextCoinDepositParser textCoinDepositParser;

    public SendMessage process(Message message) {
        AccountDocument account = accountService.getAccountFromMessage(message);
        if (FlowStatus.DEPOSIT == account.getFlowStatus()) {
            return processDepositText(account, message);
        }
        return notDepositFlow(message);
    }

    private SendMessage notDepositFlow(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        response.setText("Please use command to interact with me:\n\n" +
                "/profit - Get deposit profit\n" +
                "/deposit - Set up your deposit details\n" +
                "/restart - Clear your deposit details ");
        return response;
    }

    private SendMessage processDepositText(AccountDocument account, Message message) {

        List<CoinDeposit> coinDeposits = textCoinDepositParser.parse(message.getText());
        if (coinDeposits.isEmpty()) {
            throw new MessageProcessException("Failed to process message.\n" +
                    "Please check that your text message is correct.");
        }

        accountService.updateCoinDeposits(account, coinDeposits);

        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        StringBuilder text = new StringBuilder("Deposit details was successfully updated.\n");
        for (CoinDeposit coinDeposit : coinDeposits) {
            text.append(coinDeposit.getName())
                    .append(" was deposited on ")
                    .append(coinDeposit.getCount() * coinDeposit.getPrice())
                    .append("\n");
        }
        response.setText(text.toString());
        return response;
    }


}
