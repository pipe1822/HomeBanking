package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountStatus;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;

import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private double balance;
    private String number;
    private AccountStatus accountStatus;

    private CardDTO card;
    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account){
        this.id= account.getId();
        this.balance=account.getBalance();
        this.number=account.getNumber();
        this.transactions=account.getTransactions().stream().map(x-> new TransactionDTO(x)).collect(Collectors.toSet());
        this.card=new CardDTO(account.getCard());
        this.accountStatus=account.getAccountStatus();


    }// Aqui esta el final de la recursividad con cliente

    public Long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }

    public CardDTO getCard() {
        return card;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
