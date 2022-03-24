package com.example.coinprofit.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    String getResponseMessage(Message message);
}
