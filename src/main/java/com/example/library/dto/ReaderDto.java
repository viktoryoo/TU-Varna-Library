package com.example.library.dto;

public class ReaderDto {
  private int id;
  private String name;
  private String address;
  private int phoneNumber;
  private String email;

  public ReaderDto(int id, String name, String address, int phoneNumber, String email) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getAddress() {
    return this.address;
  }

  public int getPhoneNumber() {
    return this.phoneNumber;
  }

  public String getEmail() {
    return this.email;
  }
}
