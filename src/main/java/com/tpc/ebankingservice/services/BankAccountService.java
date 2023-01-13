package com.tpc.ebankingservice.services;

import com.tpc.ebankingservice.dtos.CustomerDTO;
import com.tpc.ebankingservice.entities.BankAccount;
import com.tpc.ebankingservice.entities.CurrentAccount;
import com.tpc.ebankingservice.entities.Customer;
import com.tpc.ebankingservice.entities.SavingAccount;
import com.tpc.ebankingservice.exceptions.BalanceNotSufficientExeption;
import com.tpc.ebankingservice.exceptions.BankAccountNotFoundException;
import com.tpc.ebankingservice.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
     Customer saveCustomer(Customer customer);
     CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;
     SavingAccount saveSavingBankAccount(double initialBalance, Long customerId, double interestedRate) throws CustomerNotFoundException;

     List<CustomerDTO> listCustomers();
     BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption;
     void credit (String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption;
     void transfer(String accountIdSource, String accountIdDescription,double amount) throws BankAccountNotFoundException, BalanceNotSufficientExeption;

     List<BankAccount> bankAccountList();
}
