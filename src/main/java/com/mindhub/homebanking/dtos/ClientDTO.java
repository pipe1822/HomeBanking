package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClientDTO {
    private long id;
    private String name;
    private String email;
    private Set <AccountDTO> accounts;

    private  Set <ClientLoanDTO> loans;

    private Set <CardDTO> card;

       public ClientDTO(Client client) {
        this.id= client.getId();
        this.name= client.getName();
        this.email=client.getEmail();
        this.accounts=client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans=client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());
        this.card=client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCard() {
        return card;
    }
}
