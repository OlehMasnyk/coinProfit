package com.example.coinprofit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum Coin {

    SLP("SMOOTH-LOVE-POTION"),
    MC("MERIT-CIRCLE"),
    IOST("IOSTOKEN"),
    CAKE("PANCAKESWAP"),
    GLMR("MOONBEAM"),
    BTTC("BITTORRENT-NEW"),
    AXS("AXIE-INFINITY"),
    ETC("ETHEREUM-CLASSIC"),
    SHIB("SHIBA-INU");


    private static final Map<String, String> NAMES_HOLDER = new HashMap<>(Coin.values().length);
    private @Getter
    final String value;

    static {
        for (Coin coin : Coin.values()) {
            NAMES_HOLDER.put(coin.getValue(), coin.name());
        }
    }

    public static String getCoinName(String name) {
        return NAMES_HOLDER.getOrDefault(name, name);
    }
}
