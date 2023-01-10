package com.tpc.ebankingservice.repositories;

import com.tpc.ebankingservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
