module com.example.library {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.bootstrapfx.core;

  opens com.example.library to javafx.fxml;
  exports com.example.library;

  opens com.example.library.controllers to javafx.fxml;
  exports com.example.library.controllers;

  requires java.sql;
  requires java.dotenv;
  requires org.hibernate.orm.core;
  requires jakarta.persistence;
  requires java.naming;
  requires org.apache.logging.log4j;

  opens com.example.library.entities;
}