package com.api.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.biblioteca.model.Loan;
import com.api.biblioteca.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Obter todos os empréstimos
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.findAll();
        return loans.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(loans);
    }

    // Obter um empréstimo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.findById(id);
        return loan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Obter os empréstimos de um cliente
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable Long customerId) {
        List<Loan> loans = loanService.findByCustomerId(customerId);
        return loans.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(loans);
    }

    // Criar um novo empréstimo
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        // verificando se o cliente já tem um empréstimo ativo
        if (loanService.checkExistingLoan(loan.getCliente().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // se cliente já tem um empréstimo ativo
        }
        Loan createdLoan = loanService.save(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    // Finalizar o empréstimo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        Optional<Loan> loan = loanService.findById(id);
        if (loan.isPresent()) {
            loanService.returnBooksAndUpdateStatus(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Prorrogar o empréstimo
    @PatchMapping("/{id}/extend")
    public ResponseEntity<Loan> extendLoan(@PathVariable Long id) {
        Optional<Loan> loan = loanService.findById(id);
        if (loan.isPresent()) {
            Loan extendedLoan = loanService.extendLoan(id);
            return ResponseEntity.ok(extendedLoan);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Atualizar o status do empréstimo
    @PatchMapping("/{id}/status")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable Long id, @RequestBody Loan.StatusEmprestimo status) {
        Optional<Loan> loan = loanService.findById(id);
        if (loan.isPresent()) {
            Loan existingLoan = loan.get();
            existingLoan.setStatus(status);
            Loan updatedLoan = loanService.save(existingLoan);
            return ResponseEntity.ok(updatedLoan);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}