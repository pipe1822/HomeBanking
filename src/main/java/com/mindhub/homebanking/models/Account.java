package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator(name="native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate; 
    private double balance;
    private AccountType accountType;
    private AccountStatus accountStatus;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER) //mapea dentro de transaccion la propiedad accounts
    private Set <Transaction> transactions= new HashSet<>(); // Set <Transactions> me da la lista de mis transacciones y me verifica que no esten repetidas  dentro de la propiedad "transactions"y hashset es lo mismo que un array vacio en la memoria interna del aplicativo que se llena con la lista

    public Account(){};


        public Account(String number, LocalDateTime creationDate, double balance){
        this.number= number;
        this.creationDate= creationDate;
        this.balance=balance;

    }

    public Account(String number, LocalDateTime creationDate, double balance, Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.transactions = transactions;
    }

    public Account(String number, LocalDateTime creationDate, double balance, Card card, Client client,AccountType accountType, AccountStatus accountStatus) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.card = card;
        this.client = client;
        this.accountType=accountType;
        this.accountStatus=accountStatus;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set <Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
