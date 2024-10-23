package com.api.biblioteca.controller;

import com.api.biblioteca.model.Loan;
import com.api.biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return loanRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanRepository.save(loan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setCustomer(loanDetails.getCustomer());
                    loan.setBooks(loanDetails.getBooks());
                    loan.setExtended(loanDetails.getExtended());
                    return ResponseEntity.ok(loanRepository.save(loan));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loanRepository.delete(loan);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
