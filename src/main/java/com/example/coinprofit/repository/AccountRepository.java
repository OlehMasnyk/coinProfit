package com.example.coinprofit.repository;

import com.example.coinprofit.document.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<AccountDocument, String> {

    List<AccountDocument> findAllByCoinDepositsIsNotNullAndNotificationTrue();
}
