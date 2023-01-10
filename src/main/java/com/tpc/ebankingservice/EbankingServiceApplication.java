package com.tpc.ebankingservice;

import com.tpc.ebankingservice.entities.*;
import com.tpc.ebankingservice.enums.AccountStatus;
import com.tpc.ebankingservice.enums.OperationType;
import com.tpc.ebankingservice.repositories.AccountOperationRepository;
import com.tpc.ebankingservice.repositories.BankAccountRepository;
import com.tpc.ebankingservice.repositories.CustomerRepository;
import com.tpc.ebankingservice.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingServiceApplication.class, args);
	}
@Bean

	CommandLineRunner start(BankService bankService){
		return args -> {
     bankService.consulter();
		};
	}
}
