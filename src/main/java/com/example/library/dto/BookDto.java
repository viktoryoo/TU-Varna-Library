package com.example.library.dto;

public class BookDto {
  private int id;
  private String title;
  private String author;
  private String genre;
  private String publisher;
  private int yearOfPublication;
  private boolean isAvailable;

  public BookDto(int id, String title, String author, String genre, String publisher, int yearOfPublication, boolean isAvailable) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.publisher = publisher;
    this.yearOfPublication = yearOfPublication;
    this.isAvailable = isAvailable;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getGenre() {
    return genre;
  }

  public String getPublisher() {
    return publisher;
  }

  public int getYearOfPublication() {
    return yearOfPublication;
  }

  public boolean isAvailable() {
    return isAvailable;
  }
}
