package com.example.vitasofttask.application.application.domain;

import com.example.vitasofttask.application.application.model.ApplicationStatus;
import com.example.vitasofttask.application.user.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application getById(Long id);
    Page<Application> findAllByUser(Customer customer, Pageable pageable);
    Page<Application> findAllByStatus(ApplicationStatus status, Pageable pageable);
    Page<Application> findAllByUserAndStatus(Customer customer, ApplicationStatus status, Pageable pageable);


}
