package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.AccountStatus;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO  {
    private long id;
    private TransactionType type;
    private double amount;

    private AccountStatus transactionStatus;

    private double newBalance ;
    private String description;
    private LocalDateTime date;


    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.newBalance= transaction.getNewBalance();
        this.transactionStatus=transaction.getTransactionStatus();
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public AccountStatus getTransactionStatus() {
        return transactionStatus;
    }
}
