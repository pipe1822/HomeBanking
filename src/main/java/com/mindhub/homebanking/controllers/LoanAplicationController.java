package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@RequestMapping("/api")
@RestController
public class LoanAplicationController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Transactional
    @PostMapping(path = "/loan")
    public ResponseEntity<String> addLoans(@NotNull Authentication authentication, @RequestBody LoanAplicationDTO loanAplicationDTO){
        if (loanAplicationDTO.getLoan()==0||loanAplicationDTO.getAmount()<=0||loanAplicationDTO.getPayments()==0||loanAplicationDTO.getNumberDSTN()==""){
            return new ResponseEntity<>("No puedes tener campos vacios",HttpStatus.FORBIDDEN);
        }
        Client client=clientRepository.findByEmail(authentication.getName());

        Loan loan = loanRepository.findById(loanAplicationDTO.getLoan());
        if (loan==null){
            return new ResponseEntity<>("No Existe el prestamo que solicitas",HttpStatus.FORBIDDEN);
        }

        if (loan.getMaxAmount()< loanAplicationDTO.getAmount()){
            return new ResponseEntity<>("Excedes el monto maximo que te podemos entregar",HttpStatus.FORBIDDEN);
        }
        boolean paymentsVerify = loan.getPayments().contains(loanAplicationDTO.getPayments());
        if (!paymentsVerify){
            return new ResponseEntity<>("No puedes escoger esa cantidad de cuotas ",HttpStatus.FORBIDDEN);
        }
        Account destinyAccount = accountRepository.findByNumber(loanAplicationDTO.getNumberDSTN());
        boolean verifyAccount = client.getAccounts().contains(destinyAccount);
        if (destinyAccount==null && !verifyAccount ){
            return new ResponseEntity<>("No te pertenece o no existe la cuenta a la cual depositaremos", HttpStatus.FORBIDDEN);
        }

        Long loanRepeat= client.getClientLoans().stream().filter(x -> x.getLoan().equals(loan)).count();

        if (loanRepeat>=1){
            return new ResponseEntity<>("Lo sientooo no podemos darte dos veces el mismo prestamo",HttpStatus.FORBIDDEN);
        }




        ClientLoan clientLoan= new ClientLoan((loanAplicationDTO.getAmount()*loan.getInterests()), loanAplicationDTO.getPayments(), client,loan);

        clientLoanRepository.save(clientLoan);



        Transaction transaction= new Transaction(TransactionType.CREDITO,loanAplicationDTO.getAmount(),loanAplicationDTO.getAmount()+destinyAccount.getBalance(),"Aprobado",LocalDateTime.now(),destinyAccount,AccountStatus.ACTIVE);
        transactionRepository.save(transaction);

        destinyAccount.setBalance(destinyAccount.getBalance()+ loanAplicationDTO.getAmount());

        return new ResponseEntity<>("Prestamo Agregado",HttpStatus.ACCEPTED);


    }





}
