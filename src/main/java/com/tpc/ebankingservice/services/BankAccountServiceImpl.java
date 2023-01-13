package com.tpc.ebankingservice.services;

import com.tpc.ebankingservice.dtos.CustomerDTO;
import com.tpc.ebankingservice.entities.*;
import com.tpc.ebankingservice.enums.OperationType;
import com.tpc.ebankingservice.exceptions.BalanceNotSufficientExeption;
import com.tpc.ebankingservice.exceptions.BankAccountNotFoundException;
import com.tpc.ebankingservice.exceptions.CustomerNotFoundException;
import com.tpc.ebankingservice.mappers.BankAccountMapperImpl;
import com.tpc.ebankingservice.repositories.AccountOperationRepository;
import com.tpc.ebankingservice.repositories.BankAccountRepository;
import com.tpc.ebankingservice.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j //lombok
public class BankAccountServiceImpl implements BankAccountService{
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;
   // Logger log= LoggerFactory.getLogger(this.getClass().getName());
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository, BankAccountMapperImpl bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
      log.info("Saving new Customer");
      Customer savedCustomer= customerRepository.save(customer);
     return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount=new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        return bankAccountRepository.save(currentAccount);


    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, Long customerId, double interestedRate) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestedRate);
        return bankAccountRepository.save(savingAccount);


    }



    @Override
    public List<CustomerDTO> listCustomers() {

        List<Customer> customers=customerRepository.findAll();
       List<CustomerDTO> customerDTOS= customers.stream().map(cust->bankAccountMapper.fromCustomer(cust)).toList();

        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account Not found"));
      return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption {
     BankAccount bankAccount=getBankAccount(accountId);
     if (bankAccount.getBalance()<amount)
         throw new BalanceNotSufficientExeption("Balance not sufficient");
         AccountOperation accountOperation=new AccountOperation();
         accountOperation.setType(OperationType.DEBIT);
         accountOperation.setDescription(description);
         accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
         accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDescription, double amount) throws BankAccountNotFoundException, BalanceNotSufficientExeption {
    debit(accountIdSource,amount,"transfert to"+accountIdDescription);
    credit(accountIdDescription,amount,"transfert from"+accountIdSource);
    }
    @Override
   public List<BankAccount> bankAccountList(){
       return bankAccountRepository.findAll();
    }
}
