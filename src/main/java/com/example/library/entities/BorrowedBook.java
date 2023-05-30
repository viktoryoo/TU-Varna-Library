package com.example.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BorrowedBook {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private int bookId;
  private int readerId;
  private LocalDate loanDate;
  private LocalDate returnDate;
  private boolean isReturned;


  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public BorrowedBook() {
  }

  public BorrowedBook(Integer id, int bookId, int readerId, LocalDate loanDate, LocalDate returnDate, boolean isReturned, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.bookId = bookId;
    this.readerId = readerId;
    this.loanDate = loanDate;
    this.returnDate = returnDate;
    this.isReturned = isReturned;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public int getId () {
    return this.id;
  }

  public int getBookId () {
    return this.bookId;
  }

  public int getReaderId () {
    return this.readerId;
  }

  public LocalDate getLoanDate() {
    return loanDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public void setReaderId(int readerId) {
    this.readerId = readerId;
  }


  public void setLoanDate(LocalDate loanDate) {
    this.loanDate = loanDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isReturned() {
    return isReturned;
  }

  public void setReturned(boolean returned) {
    isReturned = returned;
  }
}
