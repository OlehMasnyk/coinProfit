package com.example.coinprofit.parser.impl;

import com.example.coinprofit.model.CoinDeposit;
import com.example.coinprofit.parser.CoinDepositParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

@Component
public class JsonCoinDepositParser implements CoinDepositParser<InputStream> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<CoinDeposit> parse(InputStream content) {
        try {
            return objectMapper.readerForListOf(CoinDeposit.class)
                    .readValue(content);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
