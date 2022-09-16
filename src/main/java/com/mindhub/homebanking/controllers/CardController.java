package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mindhub.homebanking.models.CardStatus.CANCEL;
import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/cards")
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(x -> new CardDTO(x)).collect(toList());
    }
    @PostMapping(path = "/clients/current/cards" )
    public ResponseEntity<Object>registerCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        List<Card> cards =client.getCards().stream().filter(x->x.getCardStatus()== CardStatus.ACTIVE).collect(toList());
        if (cards.size()<6) {
            long CantidadDetipo = cards.stream().filter(x-> x.getCardType()==cardType && x.getCardColor()==cardColor).count();
            if (CantidadDetipo<1) {
                    String number;
                    Integer cvv;
                    Card cardRepeat;
                    do {
                        number = CardUtils.getCardNumber();
                        cvv = CardUtils.getCvv();
                        cardRepeat = cardRepository.findByNumber(number);
                    } while (cardRepeat != null);
                    Card card = new Card(client.getName(), cardType, cardColor, number, cvv, LocalDate.now().plusYears(5), LocalDate.now(), CardStatus.ACTIVE,client);
                    cardRepository.save(card);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
            else {
                return new ResponseEntity<>("Ya tienes una igual",HttpStatus.FORBIDDEN);

            }

            }

        return new ResponseEntity<>("No Podes Crear mas tarjetas ",HttpStatus.FORBIDDEN);
    }




        @PatchMapping("/cards/status/cancel")
        public ResponseEntity<Object> deleteCard(  String cardNumber ,Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        Card card= cardRepository.findByNumber(cardNumber);

        if (client.getCards().contains(card)) {
            card.setCardStatus(CANCEL);
            cardRepository.save(card);
            return new ResponseEntity<>(card.getCardStatus(), HttpStatus.ACCEPTED);
        }

            return new ResponseEntity<>("No es tu tarjeta perro",HttpStatus.FORBIDDEN);

    }
    };


