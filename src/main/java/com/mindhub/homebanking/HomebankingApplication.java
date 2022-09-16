package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.AccountType.CORRIENTE;
import static com.mindhub.homebanking.models.AccountType.DEBIT;
import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.CardStatus.ACTIVE;
import static com.mindhub.homebanking.models.TransactionType.CREDITO;
import static com.mindhub.homebanking.models.TransactionType.DEBITO;
import static com.mindhub.homebanking.models.AccountStatus.*;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {	
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
		public CommandLineRunner initData(ClientRepository  clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
			return (args) -> {

				Client juan= new Client("Juan","juancarpinche@gmail.com",30,passwordEncoder.encode("lacontaseña")
				);
				Client sebas =new Client ("Sebas","sebas@gmail.com",25,passwordEncoder.encode("asdfg"));
				Client pipe  =new Client("Pipe","pipe@gmail.com",28, passwordEncoder.encode("pipe123"));
				Client admin= new Client("admin","admin@gmail.com",50,passwordEncoder.encode("123lautaro"));
				clientRepository.save(admin);
				clientRepository.save(juan);
				clientRepository.save(sebas);
				clientRepository.save(pipe);

				//Se crean los clientes - empieza la creacion de tarjetas;
				Card pipeCard = new Card(pipe.getName(),CardType.DEBITO,GOLD, "2568-58974-5987-6374",251, LocalDate.now(),LocalDate.now(), ACTIVE,pipe);

				Card pipeCard2 = new Card(pipe.getName(),CardType.DEBITO,TITANIUM,"2014-5879-5236-1478",362, LocalDate.now(),LocalDate.now(),ACTIVE,pipe);

				Card sebasCard = new Card(sebas.getName(),CardType.DEBITO,SILVER,"4571-5897-9874-8547",341, LocalDate.now(),LocalDate.now(), ACTIVE,sebas);
				Card juanCard = new Card(juan.getName(),CardType.DEBITO,SILVER,"4571-5897-9874-1538",322, LocalDate.now(),LocalDate.now(), ACTIVE,juan);

				cardRepository.save(pipeCard2);
				cardRepository.save(pipeCard);
				cardRepository.save(sebasCard);
				cardRepository.save(juanCard);
				// final tarjetas inicia cuentas


				Account  cuenta1= new Account("VIN001", LocalDateTime.now(),25.02,juanCard,juan,DEBIT,AccountStatus.ACTIVE);
				accountRepository.save(cuenta1);

				Account cuenta2= new Account("VIN002",LocalDateTime.now(),50.23,pipeCard,pipe,CORRIENTE,AccountStatus.ACTIVE);
				accountRepository.save(cuenta2);

				Account cuenta3= new Account("VIN003",LocalDateTime.now(),40.2,sebasCard ,sebas,CORRIENTE,AccountStatus.ACTIVE );
				accountRepository.save(cuenta3);

				Account cuenta4= new Account("VIN004",LocalDateTime.now(),38.2,pipeCard2,pipe,DEBIT,AccountStatus.ACTIVE );
				accountRepository.save(cuenta4);


				Transaction transaction1=new Transaction(DEBITO,-15.2,(cuenta2.getBalance() - 15.2),"Pago Restaurante McDonald",LocalDateTime.now(),cuenta2,AccountStatus.ACTIVE);
				transactionRepository.save(transaction1);
				Transaction transaction2=new Transaction(CREDITO,20.2,(cuenta2.getBalance() + 20.2),"ABONO DE EMPRESA",LocalDateTime.now(),cuenta2,AccountStatus.ACTIVE);
				transactionRepository.save(transaction2);
				Transaction transaction3=new Transaction(DEBITO,-15.2,(cuenta3.getBalance() - 15.2),"Pago Restaurante McDonald",LocalDateTime.now(),cuenta3,AccountStatus.ACTIVE);
				transactionRepository.save(transaction3);
				Transaction transaction4=new Transaction(CREDITO,20.2 , (cuenta4.getBalance() + 20.2),"ABONO DE EMPRESA",LocalDateTime.now(),cuenta4,AccountStatus.ACTIVE);
				transactionRepository.save(transaction4);
				Transaction transaction5=new Transaction(CREDITO,20.2,(cuenta1.getBalance() + 20.2), "ABONO DE EMPRESA",LocalDateTime.now(),cuenta1,AccountStatus.ACTIVE);
				transactionRepository.save(transaction5);


				Loan hipotecario=new Loan("Hipotecario",500000, List.of(12,24,36,48,60),1.2);
				Loan personal = new Loan("Personal",100000, List.of( 6,12,24),1.3);
				Loan automotriz= new Loan("Automotriz",300000,List.of(6,12,24,36),1.1	);
				loanRepository.save(personal);
				loanRepository.save(automotriz);
				loanRepository.save(hipotecario);

				ClientLoan prestamoJuan1= new ClientLoan(400000,60,juan,hipotecario);
				ClientLoan prestamoJuan2= new ClientLoan(50000,12,juan,personal);
				ClientLoan prestamoSebas1=new ClientLoan(100000,24,sebas,personal);
				ClientLoan prestamoSebas2=new ClientLoan(200000,36,sebas,automotriz);
				clientLoanRepository.save(prestamoSebas2);
				clientLoanRepository.save(prestamoJuan1);
				clientLoanRepository.save(prestamoSebas1);
				clientLoanRepository.save(prestamoJuan2);







			};


		}

	}




