package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.*;

import java.math.BigInteger;
import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private String cardholder;
    private CardType cardType;
    private CardColor cardColor;
    private String number;
    private Integer cvv;

    private CardStatus cardStatus;
    private LocalDate thruDate;
    private LocalDate fromDate;



    public CardDTO(Card card){
        this.id= card.getId();
        this.cardholder= card.getCardholder();
        this.cardType=card.getCardType();
        this.cardColor=card.getCardColor();
        this.number= card.getNumber();
        this.cvv= card.getCvv();
        this.thruDate=card.getThruDate();
        this.fromDate=card.getFromDate();
        this.cardStatus=card.getCardStatus();

    }

    public Long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }
}
