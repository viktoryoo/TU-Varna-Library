package com.example.library.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlConnector {

  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");

    String url = "jdbc:mysql://localhost:3306/library";
    String username = "root";
    String password = "LibraryProject6661!";
    Connection conn = DriverManager.getConnection(url, username, password);

    Statement stmt = conn.createStatement();

    // Example for executing SQL statements and queries
    String sql = "SELECT * FROM Books";
    ResultSet rs = stmt.executeQuery(sql);
    while (rs.next()) {
      int bookId = rs.getInt("id");
      String title = rs.getString("title");
      String author = rs.getString("author");
      String genre = rs.getString("genre");
      String publisher = rs.getString("publisher");
      int yearOfPublication = rs.getInt("year_of_publication");
      boolean isAvailable = rs.getBoolean("is_available");
      // Do something with the retrieved data
    }

    rs.close();
    stmt.close();
    conn.close();
  }
}
