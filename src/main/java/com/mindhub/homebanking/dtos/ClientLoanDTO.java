package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private Long idLoan;
    private String loanName;
    private double requestedAmount;
    private Integer payments;


    public ClientLoanDTO(ClientLoan clientLoan){
        this.id= clientLoan.getId();
        this.idLoan= clientLoan.getLoan().getId();
        this.loanName=clientLoan.getLoan().getName();
        this.requestedAmount=clientLoan.getAmount();
        this.payments=clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public Integer getPayments() {
        return payments;
    }
}
