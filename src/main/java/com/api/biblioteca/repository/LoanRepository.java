package com.api.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.biblioteca.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
}

