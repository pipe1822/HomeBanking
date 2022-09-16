package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class LoanController {
    @Autowired
   private LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }
}
