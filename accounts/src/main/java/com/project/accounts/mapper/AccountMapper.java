package com.project.accounts.mapper;

import com.project.accounts.dto.AccountsDto;
import com.project.accounts.entities.Account;


//TODO: We can use mapper dependency instead of this.
public class AccountMapper {

    public static AccountsDto mapToAccountsDto(Account account, AccountsDto accountsDto) {

        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(account.getBranchAddress());

        return accountsDto;
    }

    public static Account mapToAccount(AccountsDto accountsDto, Account account) {

        account.setAccountNumber(accountsDto.getAccountNumber());
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());

        return account;
    }

}
