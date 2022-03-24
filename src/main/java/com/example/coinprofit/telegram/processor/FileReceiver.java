package com.example.coinprofit.telegram.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.InputStream;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class FileReceiver {

    public SendMessage process(Message message, Future<InputStream> file) {
        return null;
    }
}
