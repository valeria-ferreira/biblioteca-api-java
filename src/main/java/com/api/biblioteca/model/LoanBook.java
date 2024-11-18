package com.api.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "loan_books")
public class LoanBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Garantindo que o Hibernate entenda que é AUTO_INCREMENT
    @Column(name = "loan_books_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;  // Assumindo que você tem uma classe Loan

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public LoanBook() {
    }

    public LoanBook(Loan loan, Book book) {
        this.loan = loan;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanBook loanBook = (LoanBook) o;
        return Objects.equals(id, loanBook.id) &&
               Objects.equals(loan, loanBook.loan) &&
               Objects.equals(book, loanBook.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loan, book);
    }
}
