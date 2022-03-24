package com.example.coinprofit.parser.impl;

import com.example.coinprofit.model.CoinDeposit;
import com.example.coinprofit.parser.CoinDepositParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TextCoinDepositParser implements CoinDepositParser<String> {

    private static final Pattern PATTERN = Pattern.compile("(.*) ([\\d.]*) ([\\d.]*)");


    /**
     *
     * @param content - Text with coin deposits separated by enter and coin deposit values separated by space.
     *                Format:
     *                COIN1_NAME COIN1_COUNT COIN1_PRICE
     *                COIN2_NAME COIN2_COUNT COIN2_PRICE
     *                ...
     *                Example:
     *                ETC 12 25
     *                ETH 1.3 2900.42
     * @return list of parsed coin deposits
     */
    @Override
    public List<CoinDeposit> parse(String content) {
        List<CoinDeposit> coinDeposits = new ArrayList<>();
        for (String line : content.split("\n")) {
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                CoinDeposit coinDeposit = new CoinDeposit();
                coinDeposit.setName(matcher.group(1));
                coinDeposit.setCount(Double.parseDouble(matcher.group(2)));
                coinDeposit.setPrice(Double.parseDouble(matcher.group(3)));

                coinDeposits.add(coinDeposit);
            } else {
                return Collections.emptyList();
            }
        }
        return coinDeposits;
    }
}
