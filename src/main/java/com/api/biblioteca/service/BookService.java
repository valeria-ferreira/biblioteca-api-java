package com.api.biblioteca.service;

import com.api.biblioteca.model.Book;
import com.api.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  // Método para buscar todos os livros
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  // Método para buscar livro por ID
  public Optional<Book> findById(Long id) {
    return bookRepository.findById(id);
  }

  // Método para salvar (criar ou atualizar) um livro
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  // Método para excluir um livro
  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}