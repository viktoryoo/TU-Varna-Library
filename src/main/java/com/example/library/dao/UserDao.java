package com.example.library.dao;

import com.example.library.entities.User;
import com.example.library.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class UserDao implements Dao<User> {

    private EntityManager entityManager = HibernateUtil.getEntityManager();

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }

    public List<User> getAllReaders() {
        Query query = entityManager.createQuery("SELECT e FROM User e WHERE e.role = 1");

        return query.getResultList();
    }

    public Optional<User> findUser(String email, String password) {
        Query query = entityManager.createQuery("SELECT e FROM User e WHERE e.email = :email AND e.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);

        return query.getResultStream().findFirst();
    }

    @Override
    public void save(User user) {
        executeInsideTransaction(entityManager -> entityManager.persist(user));
    }

    public void update(User user, String[] params) {
        user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(user));
    }

    @Override
    public void delete(User user) {
        executeInsideTransaction(entityManager -> entityManager.remove(user));
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