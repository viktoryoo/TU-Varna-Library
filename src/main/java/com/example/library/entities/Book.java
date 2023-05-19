package com.example.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String title;
  private String author;
  private String genre;
  private String publisher;
  private int yearOfPublication;
  private boolean isAvailable;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Book(Integer id, String title, String author, String genre, String publisher, int yearOfPublication, boolean isAvailable, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.publisher = publisher;
    this.yearOfPublication = yearOfPublication;
    this.isAvailable = isAvailable;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setYearOfPublication(int yearOfPublication) {
    this.yearOfPublication = yearOfPublication;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
