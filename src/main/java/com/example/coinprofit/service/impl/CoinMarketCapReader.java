package com.example.coinprofit.service.impl;

import com.example.coinprofit.service.CoinValueReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CoinMarketCapReader implements CoinValueReader {

    private static final String BASE_URL = "https://coinmarketcap.com/currencies/";
    @Override
    public Double getPrice(String coin) {
        try {
            Document document = Jsoup.connect(getCoinUrl(coin)).get();
            return document.select(".priceValue span")
                    .stream()
                    .findAny()
                    .map(Element::text)
                    .map(x -> x.substring(1))
                    .map(x -> x.replace(",", ""))
                    .map(Double::parseDouble)
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("Failed to list coin page: " + coin);
            return null;
        }
    }

    private String getCoinUrl(String coinName) {
        return BASE_URL + coinName.toLowerCase();
    }
}
