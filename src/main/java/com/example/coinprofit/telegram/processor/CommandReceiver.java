package com.example.coinprofit.telegram.processor;

import com.example.coinprofit.telegram.command.Command;
import com.example.coinprofit.telegram.exception.MessageProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandReceiver {
    private final static Map<String, Command> COMMANDS = new HashMap<>();

    private final Command profitCommand;
    private final Command depositCommand;
    private final Command startCommand;
    private final Command restartCommand;
    private final Command notificationCommand;

    @PostConstruct
    public void init() {
        COMMANDS.put("/profit", profitCommand);
        COMMANDS.put("/deposit", depositCommand);
        COMMANDS.put("/start", startCommand);
        COMMANDS.put("/restart", restartCommand);
        COMMANDS.put("/notification", notificationCommand);
    }

    public SendMessage process(Message message) {
        Command command = COMMANDS.get(message.getText());
        if (command == null) {
            throw new MessageProcessException("Unknown command: " + message.getText());
        }
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        String responseMessage = command.getResponseMessage(message);
        response.setText(responseMessage);
        return response;
    }
}
