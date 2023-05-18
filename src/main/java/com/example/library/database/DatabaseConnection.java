package com.example.library.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
  public Connection databaseLink;

  public Connection getConnection() {
    String databaseName = "library";
    String databaseUser = "root";
    String databasePassword = "LibraryProject6661!";
    String url = "jdbc:mysql://localhost:3306/library";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return databaseLink;
  }
}
