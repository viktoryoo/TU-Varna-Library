package com.example.library.dao;

import com.example.library.entities.Book;
import com.example.library.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BookDao implements Dao<Book> {

  private EntityManager entityManager = HibernateUtil.getEntityManager();

  @Override
  public Optional<Book> get(long id) {
    return Optional.empty();
  }

  @Override
  public List<Book> getAll() {
    Query query = entityManager.createQuery("SELECT b FROM Book b");
    return query.getResultList();
  }

  public List<Book> getAllAvailable() {
    Query query = entityManager.createQuery("SELECT b FROM Book b WHERE b.isAvailable = true");
    return query.getResultList();
  }

  @Override
  public void save(Book book) {
    executeInsideTransaction(entityManager -> entityManager.persist(book));
  }

  @Override
  public void delete(Book book) {
    executeInsideTransaction(entityManager -> entityManager.remove(book));
  }

  public void update(Book book) {
    executeInsideTransaction(entityManager -> entityManager.merge(book));
  }

  private void executeInsideTransaction(Consumer<EntityManager> action) {
    EntityTransaction tx = entityManager.getTransaction();
    try {
      tx.begin();
      action.accept(entityManager);
      tx.commit();
    } catch (RuntimeException e) {
      tx.rollback();
      throw e;
    }
  }
}
