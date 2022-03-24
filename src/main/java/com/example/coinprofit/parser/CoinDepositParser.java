package com.example.coinprofit.parser;

import com.example.coinprofit.model.CoinDeposit;

import java.io.InputStream;
import java.util.List;

public interface CoinDepositParser<T> {

    List<CoinDeposit> parse(T content);
}
