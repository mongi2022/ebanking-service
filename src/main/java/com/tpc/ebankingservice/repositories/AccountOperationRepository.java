package com.tpc.ebankingservice.repositories;

import com.tpc.ebankingservice.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

}
