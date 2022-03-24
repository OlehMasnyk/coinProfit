package com.example.coinprofit.service.impl;

import com.example.coinprofit.document.AccountDocument;
import com.example.coinprofit.document.CoinDepositDocument;
import com.example.coinprofit.document.FlowStatus;
import com.example.coinprofit.mapper.AccountMapper;
import com.example.coinprofit.mapper.CoinDepositMapper;
import com.example.coinprofit.model.CoinDeposit;
import com.example.coinprofit.repository.AccountRepository;
import com.example.coinprofit.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CoinDepositMapper coinDepositMapper;

    @Override
    public AccountDocument save(AccountDocument accountDocument) {
        return accountRepository.save(accountDocument);
    }

    @Override
    public AccountDocument getAccountFromMessage(Message message) {
        return getAccountByAccountId(message.getFrom().getId())
                .orElseGet(() -> save(accountMapper.toEntity(message)));
    }

    @Override
    public List<AccountDocument> getAllWithDepositDetail() {
        return accountRepository.findAllByCoinDepositsIsNotNullAndNotificationTrue();
    }

    @Override
    public Optional<AccountDocument> getAccountByAccountId(Long accountId) {
        return accountRepository.findById(String.valueOf(accountId));
    }

    @Override
    public void updateCoinDeposits(AccountDocument account, List<CoinDeposit> coinDeposits) {
        if (account.getCoinDeposits() == null) {
            account.setCoinDeposits(new ArrayList<>());
        }
        List<CoinDepositDocument> coins = coinDeposits.stream()
                .map(coinDepositMapper::toEntity)
                .collect(Collectors.toList());
        account.getCoinDeposits().addAll(coins);
        account.setFlowStatus(FlowStatus.MAIN);
        save(account);
    }
}
