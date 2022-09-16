package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository; // creo un objeto de tipo ClientRepository para interactuar con la information de la base de datos que tiene la interface
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(x -> new ClientDTO(x)).collect(toList());
        //findall es igual a tomar todos los clientes en un tipo lista, despues lo convierto en una coleccion  con un stream despues lo mapeamos para crear todos los clientes en clientDTO y lo convertimos en un listado de coleccion
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

        @PostMapping(path = "/clients")
        public ResponseEntity<Object> registerClient
                (@RequestParam String name, @RequestParam Integer age,
                @RequestParam String email, @RequestParam String password){

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age==0){

                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

            }

            if (clientRepository.findByEmail(email) != null) {
                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
            }

            Client clientRegister =new Client(name, email, age, passwordEncoder.encode(password));
            clientRepository.save(clientRegister);
            // Hasta aqui termina la creacion del cliente

            Account accountRepeat;
            String number;
            do {
                number ="VIN"+ (Math.round(Math.random() * 999999)) ;
                accountRepeat = accountRepository.findByNumber(number);
            } while (accountRepeat != null);
            Account account = new Account(number, LocalDateTime.now(), 0.0, clientRegister  );
            accountRepository.save(account);
            // Hasta aqui se crea la cuenta del cliente registrado


            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @GetMapping("/clients/current")
        public ClientDTO getAllClientCurrent(Authentication authentication) {

            return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }

}




