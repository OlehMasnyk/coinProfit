package com.example.coinprofit.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class AccountDocument {

    @MongoId
    private String id;
    private String accountName;
    private String chatId;
    private List<CoinDepositDocument> coinDeposits;
    private FlowStatus flowStatus;
    private Boolean notification = Boolean.FALSE;
    @CreatedDate
    private LocalDateTime createdAt;
}
