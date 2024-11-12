package com.api.biblioteca.controller;

import com.api.biblioteca.model.Customer;
import com.api.biblioteca.model.Loan;
import com.api.biblioteca.service.CustomerService;
import com.api.biblioteca.repository.CustomerRepository;
import com.api.biblioteca.service.LoanService;

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

    // Encontrar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Encontrar cliente pelo nome
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Customer>> findCustomerByName(@PathVariable String name) {
        List<Customer> customers = customerService.findByName(name);
        return customers.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(customers);
    }

    // Encontrar cliente pela data de nascimento
    @GetMapping("/birthdate/{birthDate}")
    public ResponseEntity<List<Customer>> findCustomerByBirthDate(@PathVariable String birthDate) {
        List<Customer> customers = customerService.findByBirthDate(birthDate);
        return customers.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(customers);
    }

    // Encontrar empréstimos de um cliente pelo ID
    @GetMapping("/{id}/loans")
    public ResponseEntity<List<Loan>> findLoansByCustomerId(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            List<Loan> loans = customer.get().getEmprestimos();
            return loans.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(loans);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Criar um novo cliente
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    // Excluir um cliente, desde que não possua empréstimos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent() && customer.get().getEmprestimos().isEmpty()) {
            customerService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Cliente com empréstimos não pode ser excluído
    }

    // Atualizar dados do cliente (exceto status)
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> existingCustomer = customerService.findById(id);
        if (existingCustomer.isPresent()) {
            customer.setId(id); // Garantir que o ID do cliente será mantido
            Customer updatedCustomer = customerService.save(customer);
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Atualizar o status do cliente (ATIVO / INATIVO)
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
}