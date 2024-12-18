package com.api.biblioteca.repository;

import com.api.biblioteca.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.nome = :nome")
    List<Customer> findByNome(String nome);
    List<Customer> findByDataNascimento(LocalDate dataNascimento);
}
