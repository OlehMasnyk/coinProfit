package com.example.coinprofit.service;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.model.CoinDeposit;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountDocument save(AccountDocument accountDocument);

    AccountDocument getAccountFromMessage(Message message);

    List<AccountDocument> getAllWithDepositDetail();

    Optional<AccountDocument> getAccountByAccountId(Long accountId);

    void updateCoinDeposits(AccountDocument account, List<CoinDeposit> coinDeposits);
}
