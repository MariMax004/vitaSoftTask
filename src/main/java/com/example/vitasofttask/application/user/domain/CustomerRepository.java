package com.example.vitasofttask.application.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsByEmail(String email);

    Customer getUserByEmail(String email);

    Customer getUserById(Long id);

    List<Customer> findAll();

    @Query(value = "select *from customer", nativeQuery = true)
    List<Customer> getAllUsers();



}
