package com.api.biblioteca.repository;

import com.api.biblioteca.model.Book;
import com.api.biblioteca.model.Book.StatusLivro;  // Alteração: importando o enum correto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar todos os livros com um determinado status (disponível, emprestado)
    List<Book> findByStatus(StatusLivro status);  // Alteração: utilizando StatusLivro

    // Buscar um livro pelo título
    Optional<Book> findByTitulo(String titulo);

    // Buscar um livro pelo ISBN
    Optional<Book> findByIsbn(String isbn);

    // Verificar se existe um livro com determinado ISBN
    boolean existsByIsbn(String isbn);

    // Buscar livros que estão emprestados através da tabela de junção
    List<Book> findByLoans_Id(Long loanId);  // Isso permanece o mesmo
}
