package com.api.biblioteca.controller;

import com.api.biblioteca.model.Book;
import com.api.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  // Obter todos os livros
  @GetMapping
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = bookService.findAll();
    return books.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(books);
  }

  // Obter um livro por ID
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    Optional<Book> book = bookService.findById(id);
    return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
