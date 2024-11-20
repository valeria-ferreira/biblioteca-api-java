package com.api.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.biblioteca.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByCliente_IdAndStatus(Long clienteId, Loan.StatusEmprestimo status);
    List<Loan> findByCliente_Id(Long customerId);
}
