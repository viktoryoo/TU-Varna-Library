package com.example.library.dto;

import java.util.Date;

public class LoanDto {
  private int id;
  private int bookId;
  private int readerId;
  private Date loanDate;
  private Date returnDate;

  public LoanDto(int id, int bookId, int readerId, Date loanDate, Date returnDate) {
    this.id = id;
    this.bookId = bookId;
    this.readerId = readerId;
    this.loanDate = loanDate;
    this.returnDate = returnDate;
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

  public Date getLoanDate () {
    return this.loanDate;
  }

  public Date getReturnDate () {
    return this.returnDate;
  }
}
