package com.api.biblioteca.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer cliente; // Relacionamento com Customer

    @ManyToMany
    @JoinTable(
      name = "loan_books", 
      joinColumns = @JoinColumn(name = "loan_id"), 
      inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> livros; // Relacionamento com a tabela de associação "loan_books"

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public List<Book> getLivros() {
        return livros;
    }

    public void setLivros(List<Book> livros) {
        this.livros = livros;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public enum StatusEmprestimo {
        EMPRESTADO, DISPONIVEL, ATRASADO, PRORROGADO
    }
    
    // Método para verificar se o empréstimo está atrasado
    public boolean isAtrasado() {
        return LocalDate.now().isAfter(dataDevolucao) && status == StatusEmprestimo.EMPRESTADO;
    }
    
    // Método para atualizar o status do empréstimo
    public void atualizarStatus() {
        if (isAtrasado()) {
            this.status = StatusEmprestimo.ATRASADO;
        } else if (dataDevolucao.isAfter(LocalDate.now()) && status == StatusEmprestimo.EMPRESTADO) {
            this.status = StatusEmprestimo.PRORROGADO;
        }
    }
}
