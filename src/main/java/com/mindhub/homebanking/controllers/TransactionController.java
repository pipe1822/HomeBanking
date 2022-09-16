package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.models.TransactionType.CREDITO;
import static com.mindhub.homebanking.models.TransactionType.DEBITO;
import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(x -> new TransactionDTO(x)).collect(toList());
    };

    @Transactional
    @PostMapping(path = "/clients/current/accounts/transactions")
    public ResponseEntity<Object> register
            (Authentication authentication,@RequestParam String accountNumberORG,@RequestParam String accountNumberDSTNO, @RequestParam double ammount,@RequestParam String description){

        Client clienteAutenticado=clientRepository.findByEmail(authentication.getName());
        Account accountORG=accountRepository.findByNumber(accountNumberORG);
        Account accountDSTNO=accountRepository.findByNumber(accountNumberDSTNO);


        if (accountNumberDSTNO.isEmpty() || accountNumberORG.isEmpty() || description.isEmpty() || ammount==0){

            return new ResponseEntity<>("No pueden haber campos vacios", HttpStatus.FORBIDDEN);

        } else if (accountDSTNO != accountORG && accountORG != null && accountORG.getNumber().equals(accountNumberORG) && accountORG.getBalance()>ammount) {


        Transaction transactionORG= new Transaction(DEBITO,-ammount,accountORG.getBalance()-ammount,description, LocalDateTime.now(),accountORG, AccountStatus.ACTIVE);
        Transaction transactionDSTNO=new Transaction(CREDITO,ammount,accountDSTNO.getBalance()+ammount,description,LocalDateTime.now(),accountDSTNO,AccountStatus.ACTIVE);

        transactionRepository.save(transactionDSTNO);
        transactionRepository.save(transactionORG);


        accountORG.setBalance(accountORG.getBalance()-ammount);
        accountDSTNO.setBalance(accountDSTNO.getBalance()+ammount);
            return new ResponseEntity<>("Transferencia Exitosa",HttpStatus.CREATED);
        }

      return new ResponseEntity<>("ESTA MAL", HttpStatus.FORBIDDEN);
    };

}
