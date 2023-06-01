package com.example.library.dao;

import com.example.library.entities.BorrowedBook;
import com.example.library.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BorrowedBookDao implements Dao<BorrowedBook> {

  private EntityManager entityManager = HibernateUtil.getEntityManager();

  @Override
  public Optional<BorrowedBook> get(long id) {
    return Optional.empty();
  }

  @Override
  public List<BorrowedBook> getAll() {
    Query query = entityManager.createQuery("SELECT b FROM BorrowedBook b");
    return query.getResultList();
  }

  @Override
  public void save(BorrowedBook borrowedBook) {
    executeInsideTransaction(entityManager -> entityManager.persist(borrowedBook));
  }

  @Override
  public void delete(BorrowedBook borrowedBook) {
    executeInsideTransaction(entityManager -> entityManager.remove(borrowedBook));
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

  public List<BorrowedBook> getAllByReaderId(int readerId) {
    Query query =
        entityManager.createQuery(
            "SELECT b FROM BorrowedBook b WHERE b.readerId = :readerId AND b.isReturned = false");
    query.setParameter("readerId", readerId);
    return query.getResultList();
  }

  public Optional<BorrowedBook> getBorrowedReaderId(Integer readerId, Integer bookId) {
    Query query =
        entityManager.createQuery(
            "SELECT b FROM BorrowedBook b WHERE b.readerId = :readerId AND b.bookId = :bookId AND b.isReturned = false");
    query.setParameter("readerId", readerId);
    query.setParameter("bookId", bookId);
    return query.getResultList().stream().findFirst();
  }

  public void update(BorrowedBook book) {
    executeInsideTransaction(entityManager -> entityManager.merge(book));
  }

}
