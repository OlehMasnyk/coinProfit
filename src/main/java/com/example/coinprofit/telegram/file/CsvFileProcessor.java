package com.example.coinprofit.telegram.file;


import com.example.coinprofit.parser.impl.CsvCoinDepositParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class CsvFileProcessor implements FileProcessor {

    private final CsvCoinDepositParser csvCoinDepositParser;

    @Override
    public String process(Message message) {
//        GetFile getFileMethod = new GetFile();
//        getFileMethod.setFileId(update.getMessage().getDocument().getFileId());
//        return downloadFile(execute(getFileMethod).getFilePath());
        return null;
    }
}
