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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.biblioteca.model.Book;
import com.api.biblioteca.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  // Obter todos os livros
  @GetMapping({"/"})
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = bookService.findAll();
    return books.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(books);
  }

  // Obter um livro por ID
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    Optional<Book> book = bookService.findById(id);
    if (book.isPresent()) {
      // Escondendo o campo 'loans' na serialização para evitar o loop
      book.get().setLoans(null);  // ou usar @JsonIgnore no campo 'loans'
      return ResponseEntity.ok(book.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}
  // Criar um novo livro
  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    Book createdBook = bookService.save(book);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
  }
  

  // Inativar um livro
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    Optional<Book> book = bookService.findById(id);
    if (book.isPresent()) {
      bookService.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  // Atualizar dados do livro (exceto status)
  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
    Optional<Book> existingBook = bookService.findById(id);
    if (existingBook.isPresent()) {
      book.setId(id); // garantindo que o ID do livro será mantido
      Book updatedBook = bookService.save(book);
      return ResponseEntity.ok(updatedBook);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  // Atualizar o status do livro
  @PatchMapping("/{id}/status")
  public ResponseEntity<Book> updateBookStatus(@PathVariable Long id, @RequestBody Book.StatusLivro status) {
    Optional<Book> book = bookService.findById(id);
    if (book.isPresent()) {
      Book existingBook = book.get();
      existingBook.setStatus(status);
      Book updatedBook = bookService.save(existingBook);
      return ResponseEntity.ok(updatedBook);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
