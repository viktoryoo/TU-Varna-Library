package com.example.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
  public Connection databaseLink;

  public Connection getConnection() {
    Dotenv dotenv = Dotenv.load();

    String databaseUser = dotenv.get("DATABASE_USER");
    String databasePassword = dotenv.get("DATABASE_PASSWORD");
    String url = dotenv.get("DATABASE_URL");

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return databaseLink;
  }
}
