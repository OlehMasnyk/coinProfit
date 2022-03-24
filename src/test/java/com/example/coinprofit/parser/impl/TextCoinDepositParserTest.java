package com.example.coinprofit.parser.impl;

import com.example.coinprofit.model.CoinDeposit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextCoinDepositParserTest {

    private TextCoinDepositParser textCoinDepositParser;

    @BeforeEach
    void setUp() {
        textCoinDepositParser = new TextCoinDepositParser();
    }
    @Test
    void parse() {
        String content =
                "IOST 2941 0.02479\n" +
                "GLMR 6.5 2.8107\n" +
                "CAKE 5.11 6.37\n" +
                "MC 30.03 2.27";

        List<CoinDeposit> parse = textCoinDepositParser.parse(content);

        assertEquals(4, parse.size());
        assertEquals("IOST", parse.get(0).getName());
        assertEquals(2941, parse.get(0).getCount());
        assertEquals(0.02479, parse.get(0).getPrice());

    }
}