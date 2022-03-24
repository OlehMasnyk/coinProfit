package com.example.coinprofit.telegram.file;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileProcessor {

    String process(Message message);
}
