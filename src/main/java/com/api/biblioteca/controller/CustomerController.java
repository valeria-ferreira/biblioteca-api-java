package com.api.biblioteca.controller;

import com.api.biblioteca.model.Customer;
import com.api.biblioteca.model.Loan;
import com.api.biblioteca.service.CustomerService;
import com.api.biblioteca.repository.CustomerRepository;
import com.api.biblioteca.service.LoanService;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanService loanService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Customer>> findCustomerByName(@PathVariable String name) {
        List<Customer> customers = customerService.findByName(name);
        return customers.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(customers);
    }

    @GetMapping("/birthdate/{birthDate}")
    public ResponseEntity<List<Customer>> findCustomerByBirthDate(@PathVariable String birthDate) {
        List<Customer> customers = customerService.findByBirthDate(birthDate);
        return customers.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent() && customer.get().getEmprestimos().isEmpty()) {
            customerService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Customer> updateCustomerStatus(@PathVariable Long id, @RequestBody Customer.StatusCliente status) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setStatus(status);
            Customer updatedCustomer = customerService.save(existingCustomer);
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Método não permitido.");
    }
}
