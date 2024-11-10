package com.api.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.biblioteca.model.Loan;
import com.api.biblioteca.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    // Método para obter todos os empréstimos
    public List<Loan> findAll() {
        return loanRepository.findAll(); 
    }

    // Método para buscar um empréstimo por ID
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id); 
    }

    // Método para buscar empréstimos de um cliente específico
    public List<Loan> findByCustomerId(Long customerId) {
        return loanRepository.findByCliente_Id(customerId); 
    }

    // Método para salvar ou atualizar um empréstimo
    public Loan save(Loan loan) {
        return loanRepository.save(loan); 
    }

    // Método para verificar se um cliente já tem um empréstimo ativo
    public boolean checkExistingLoan(Long customerId) {
       
        return loanRepository.existsByCliente_IdAndStatus(customerId, Loan.StatusEmprestimo.EMPRESTADO); 
    }

    // Método para retornar livros e atualizar o status do empréstimo
    public void returnBooksAndUpdateStatus(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isPresent()) {
            Loan existingLoan = loan.get();
            existingLoan.setStatus(Loan.StatusEmprestimo.DISPONIVEL); 
            loanRepository.save(existingLoan); 
        }
    }

    // Método para prorrogar um empréstimo
    public Loan extendLoan(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isPresent()) {
            Loan existingLoan = loan.get();
            return loanRepository.save(existingLoan); 
        }
        return null;
    }
}