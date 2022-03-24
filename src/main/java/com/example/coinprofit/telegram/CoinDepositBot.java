package com.example.coinprofit.telegram;

import com.example.coinprofit.telegram.exception.MessageProcessException;
import com.example.coinprofit.telegram.processor.CommandReceiver;
import com.example.coinprofit.telegram.processor.FileReceiver;
import com.example.coinprofit.telegram.processor.TextReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class CoinDepositBot extends TelegramLongPollingBot {

    private @Value("${telegram.bot.name}") String botUsername;
    private @Value("${telegram.bot.token}") String botToken;

    private final CommandReceiver commandReceiver;
    private final TextReceiver textReceiver;
    private final FileReceiver fileReceiver;
    private final ExecutorService fileDownloadExecutors;


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                selectProcessor(update);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (MessageProcessException e) {
                defaultResponse(update.getMessage(), e.getMessage());
            }
        }

    }

    private void selectProcessor(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        if (message.isCommand()) {
            execute(commandReceiver.process(message));
        } else if (message.hasDocument()) {
            execute(fileReceiver.process(message, getFile(message)));
        } else if (message.hasText()) {
            execute(textReceiver.process(message));
        }
    }

    private Future<InputStream> getFile(Message message) {
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(message.getDocument().getFileId());
        Callable<InputStream> downloadingFile = () -> downloadFileAsStream(execute(getFileMethod).getFilePath());
        return fileDownloadExecutors.submit(downloadingFile);
    }

    private SendMessage defaultResponse(Message message, String details) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        response.setText("Something went wrong.\n\nHere is a details:\n" + details);
        return response;
    }
}
