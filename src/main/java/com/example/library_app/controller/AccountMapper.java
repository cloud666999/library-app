package com.example.library_app.controller;

import com.example.library_app.controller.validation.AdminAccountReq;
import com.example.library_app.model.Account;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(AccountReq accountReq);

    Account toAccount(AdminAccountReq adminAccountReq);

    AccountResp toAccountResp(Account accountReq);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(AccountUpdateReq req, @MappingTarget Account account);
}
