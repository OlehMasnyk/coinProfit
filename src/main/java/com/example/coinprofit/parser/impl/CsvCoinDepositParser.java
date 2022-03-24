package com.example.coinprofit.parser.impl;

import com.example.coinprofit.model.CoinDeposit;
import com.example.coinprofit.parser.CoinDepositParser;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class CsvCoinDepositParser implements CoinDepositParser<InputStream> {

    @Override
    public List<CoinDeposit> parse(InputStream content) {
        try (Reader reader = new InputStreamReader(content)) {
            return new CsvToBeanBuilder<CoinDeposit>(reader)
                    .withSeparator(';')
                    .withType(CoinDeposit.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
