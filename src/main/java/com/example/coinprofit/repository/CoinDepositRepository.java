package com.example.coinprofit.repository;

import com.example.coinprofit.document.CoinDepositDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoinDepositRepository extends MongoRepository<CoinDepositDocument, String> {

}
