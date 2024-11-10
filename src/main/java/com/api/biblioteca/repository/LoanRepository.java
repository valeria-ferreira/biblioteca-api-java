package com.api.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.biblioteca.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // verificando se existe um empréstimo com status "EMPRESTADO" para um cliente
    boolean existsByCliente_IdAndStatus(Long clienteId, Loan.StatusEmprestimo status);

    // buscando todos os empréstimos de um cliente específico
    List<Loan> findByCliente_Id(Long clienteId);
}