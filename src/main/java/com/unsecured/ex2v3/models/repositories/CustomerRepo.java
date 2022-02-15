package com.unsecured.ex2v3.models.repositories;


import com.unsecured.ex2v3.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String integer);

    Optional<Customer> findById(Integer integer);

    List<Customer> findAll();
}

