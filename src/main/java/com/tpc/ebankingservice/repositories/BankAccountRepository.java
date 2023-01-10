package com.tpc.ebankingservice.repositories;

import com.tpc.ebankingservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
