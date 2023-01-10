package com.tpc.ebankingservice.services;

import com.tpc.ebankingservice.entities.AccountOperation;
import com.tpc.ebankingservice.entities.CurrentAccount;
import com.tpc.ebankingservice.entities.Customer;
import com.tpc.ebankingservice.entities.SavingAccount;
import com.tpc.ebankingservice.enums.AccountStatus;
import com.tpc.ebankingservice.enums.OperationType;
import com.tpc.ebankingservice.repositories.AccountOperationRepository;
import com.tpc.ebankingservice.repositories.BankAccountRepository;
import com.tpc.ebankingservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
   private CustomerRepository customerRepository;
  private AccountOperationRepository accountOperationRepository;
    public BankService(CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository) {
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    public void consulter(){
        Stream.of("Mongi", "Adem", "Faten").forEach(name -> {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(name + "@email.com");
            customerRepository.save(customer);
        });
        customerRepository.findAll().forEach(c->{
            CurrentAccount currentAccount=new CurrentAccount();
            currentAccount.setBalance(Math.random()*90000);
            currentAccount.setStatus(AccountStatus.CREATED);
            currentAccount.setCustomer(c);
            currentAccount.setOverDraft(9000);
            currentAccount.setId(UUID.randomUUID().toString());
            bankAccountRepository.save(currentAccount);
            SavingAccount savingAccount=new SavingAccount();
            savingAccount.setInterestRate(5.5);
            savingAccount.setBalance(8000);
            savingAccount.setCustomer(c);
            savingAccount.setStatus(AccountStatus.ACTIVATED);
            savingAccount.setId(UUID.randomUUID().toString());
            bankAccountRepository.save(savingAccount);
        });
        bankAccountRepository.findAll().forEach(acc->{
            for (int i = 0; i < 5; i++) {
                AccountOperation accountOperation=new AccountOperation();
                accountOperation.setOperationDate(new Date());
                accountOperation.setAmount(Math.random()*12000);
                accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                accountOperation.setBankAccount(acc);
                accountOperationRepository.save(accountOperation);
            }
//				BankAccount bankAccount=bankAccountRepository.findById("038946cc-db90-4a55-8f5e-9a1505e89e24").orElse(null);
//				System.out.println("********************");
//				System.out.println(bankAccount.getId());
//				System.out.println(bankAccount.getBalance());
//				System.out.println(bankAccount.getBalance());
//				System.out.println(bankAccount.getCreatedAt());
//				System.out.println(bankAccount.getCustomer().getName());
//				if (bankAccount instanceof CurrentAccount) {
//					System.out.println(((CurrentAccount) bankAccount).getOverDraft());
//
//				}else if (bankAccount instanceof SavingAccount){
//					System.out.println(((SavingAccount) bankAccount).getInterestRate());
//
//				}
//      bankAccount.getAccountOperations().forEach(op->{
//		  System.out.println(op.getType());
//		  System.out.println(op.getAmount());
//		  System.out.println(op.getOperationDate());
//
//	  });

        });
    }
}
