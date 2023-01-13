package com.tpc.ebankingservice;

import com.tpc.ebankingservice.entities.*;
import com.tpc.ebankingservice.enums.AccountStatus;
import com.tpc.ebankingservice.enums.OperationType;
import com.tpc.ebankingservice.exceptions.BalanceNotSufficientExeption;
import com.tpc.ebankingservice.exceptions.BankAccountNotFoundException;
import com.tpc.ebankingservice.exceptions.CustomerNotFoundException;
import com.tpc.ebankingservice.repositories.AccountOperationRepository;
import com.tpc.ebankingservice.repositories.BankAccountRepository;
import com.tpc.ebankingservice.repositories.CustomerRepository;
import com.tpc.ebankingservice.services.BankAccountService;
import com.tpc.ebankingservice.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingServiceApplication.class, args);
	}
@Bean

	CommandLineRunner start(BankAccountService bankAccountService){
		return args -> {
     Stream.of("Mongi","Faten","Adem").forEach(name->{
		Customer customer=new Customer();
		customer.setName(name);
		customer.setEmail(name+"@eamil.com");
		 bankAccountService.saveCustomer(customer);
	 });
	 bankAccountService.listCustomers().forEach(customer->{
		 try {
			 bankAccountService.saveCurrentBankAccount(Math.random()*90000,customer.getId(),9000);
			 bankAccountService.saveSavingBankAccount(Math.random()*12000, customer.getId(), 5.5);

				 List<BankAccount> bankAccounts=bankAccountService.bankAccountList();
			 for (BankAccount bankAccount:bankAccounts ) {
				 for (int i = 0; i < 10; i++) {
					 bankAccountService.credit(bankAccount.getId(),10000+Math.random()*120000,"credit");
                     bankAccountService.debit(bankAccount.getId(),1000+Math.random()*9000,"debit");
				 }


			 }

		 } catch (CustomerNotFoundException e) {
         e.printStackTrace();
		 } catch (BankAccountNotFoundException | BalanceNotSufficientExeption e) {
			e.printStackTrace();
		 }
	 });
		};
	}
}
