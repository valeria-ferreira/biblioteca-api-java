package com.api.biblioteca.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do livro")
    private Long id;

    @Schema(description = "Título do livro")
    private String titulo;

    @Schema(description = "ISBN do livro")
    private String isbn;

    @Schema(description = "Autor do livro")
    private String autor;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do livro")
    private StatusLivro status;

    // Remover ou esconder o relacionamento ManyToMany com a entidade Loan no Swagger
    @Schema(hidden = true) // Esconde o campo 'loans' para não aparecer no Swagger
    @ManyToMany
    @JoinTable(
      name = "loan_books", 
      joinColumns = @JoinColumn(name = "book_id"), 
      inverseJoinColumns = @JoinColumn(name = "loan_id"))
    private List<Loan> loans;  // Relacionamento ManyToMany com a entidade Loan

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public StatusLivro getStatus() {
        return status;
    }

    public void setStatus(StatusLivro status) {
        this.status = status;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public enum StatusLivro {
        DISPONIVEL, EMPRESTADO, RESERVADO
    }
}
