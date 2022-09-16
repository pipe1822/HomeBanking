package com.mindhub.homebanking.dtos;

public class LoanAplicationDTO {

    private long loan;
    private double amount;
    private Integer payments;
    private String numberDSTN;

    public LoanAplicationDTO() {
    }

    public LoanAplicationDTO(long loan, Integer amount, Integer payments, String numberDSTN) {
        this.loan = loan;
        this.amount = amount;
        this.payments = payments;
        this.numberDSTN = numberDSTN;
    }

    public long getLoan() {
        return loan;
    }

    public void setLoan(long loan) {
        this.loan = loan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getNumberDSTN() {
        return numberDSTN;
    }

    public void setNumberDSTN(String numberDSTN) {
        this.numberDSTN = numberDSTN;
    }
}
