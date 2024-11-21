package com.api.biblioteca.service;

import com.api.biblioteca.model.Book;
import com.api.biblioteca.model.Customer;
import com.api.biblioteca.model.Loan;
import com.api.biblioteca.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
    return customerRepository.findAll();
  }


    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findByName(String nome) {
        return customerRepository.findByNome(nome);
    }

    // Alteração no método para receber LocalDate em vez de String
    public List<Customer> findByDataNascimento(String birthDate) {
        // O formato padrão ISO 8601 (yyyy-MM-dd) é aceito diretamente
        LocalDate date = LocalDate.parse(birthDate);  // Converte a string para LocalDate
        return customerRepository.findByDataNascimento(date);  // Passa o LocalDate para a consulta
    }
    
    
    

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}