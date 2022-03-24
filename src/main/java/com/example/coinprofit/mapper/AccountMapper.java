package com.example.coinprofit.mapper;

import com.example.coinprofit.document.AccountDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.Message;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "coinDeposits", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "flowStatus", expression = "java(com.example.coinprofit.document.FlowStatus.MAIN)")
    @Mapping(target = "id", source = "from.id")
    @Mapping(target = "accountName", source = "from.firstName")
    @Mapping(target = "chatId", source = "chat.id")
    AccountDocument toEntity(Message message);

}
