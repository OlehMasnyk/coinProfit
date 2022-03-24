package com.example.coinprofit.mapper;

import com.example.coinprofit.document.CoinDepositDocument;
import com.example.coinprofit.dto.CoinProfitDto;
import com.example.coinprofit.model.CoinDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoinDepositMapper {

    CoinDepositDocument toEntity(CoinDeposit coinDeposit);

    @Mapping(target = "coin", source = "coinDeposit.name")
    @Mapping(target = "buyPrice", source = "coinDeposit.price")
    @Mapping(target = "count", source = "coinDeposit.count")
    @Mapping(target = "currentPrice", source = "currentPrice")
    @Mapping(target = "profit", expression = "java((coinDeposit.getCount() * currentPrice) - (coinDeposit.getCount() * coinDeposit.getPrice()))")
    CoinProfitDto toDto(CoinDepositDocument coinDeposit, Double currentPrice);

}
