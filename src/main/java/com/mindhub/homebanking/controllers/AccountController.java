package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;



    @RequestMapping("/api")
    @RestController
    public class AccountController {
        @Autowired
        private AccountRepository accountRepository; // creo un objeto de tipo ClientRepository para interactuar con la information de la base de datos que tiene la interface
        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private CardRepository cardRepository;

        @Autowired
        private TransactionRepository transactionRepository;

        @GetMapping("/accounts")
        public List<AccountDTO> getAccounts() {
            return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
        }


        @PostMapping(path = "/clients/current/accounts" )
        public ResponseEntity<Object> createAccount
                (Authentication authentication, @RequestParam CardColor cardColor, @RequestParam AccountType accountType) {
            Client client = clientRepository.findByEmail(authentication.getName());
            long card=client.getCards().stream().filter(x ->x.getCardColor()==cardColor ).count();

                if (client.getAccounts().size()<3) {
                    if (card>0){
                        return new ResponseEntity<>("Escoge otro color de tarjeta",HttpStatus.FORBIDDEN);
                    }
                    Account accountRepeat;
                    String number;
                    do {
                        number ="VIN"+ (Math.round(Math.random() * 999999)) ;
                        accountRepeat = accountRepository.findByNumber(number);
                    } while (accountRepeat != null);
                    Card cardClient = new Card(client.getName(), CardType.DEBITO,cardColor, CardUtils.getCardNumber(),CardUtils.getCvv(), LocalDate.now().plusYears(5),LocalDate.now(),CardStatus.ACTIVE,client);

                    cardRepository.save(cardClient);


                    Account account = new Account(number, LocalDateTime.now(), 0.0,cardClient, client,accountType,AccountStatus.ACTIVE);
                    accountRepository.save(account);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
                else {
                return new ResponseEntity<>("No Podes Crear mas Cuentas",HttpStatus.FORBIDDEN);
                }
        }
        @GetMapping("/accounts/prueba")
        public AccountDTO getAccounts(@RequestParam String number) {
            Account prueba1=accountRepository.findByNumber(number);
            AccountDTO prueba=new AccountDTO(prueba1);
            return prueba;
        }
        @PatchMapping("/accounts/bloquear")
        public ResponseEntity<Object> deleteAccounts(Authentication authentication, @RequestParam String number){

            Account account=accountRepository.findByNumber(number);
            account.setAccountStatus(AccountStatus.BLOCK);
            accountRepository.save(account);

            double transactions= account.getTransactions().size();
            account.getTransactions().stream().forEach(x->{
                x.setTransactionStatus(AccountStatus.BLOCK);
                transactionRepository.save(x);
            } );


            return new ResponseEntity<>("prueba",HttpStatus.ACCEPTED);

        }




    }

