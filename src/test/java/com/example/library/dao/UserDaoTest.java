package com.example.library.dao;

import com.example.library.entities.Role;
import com.example.library.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDaoTest {
    UserDao userDao = new UserDao();

    @Test
    void getAll() {
        userDao.save(new User(null, "test", "test", 123, "test", Role.ADMIN, "test"));
        userDao.save(new User(null, "test", "test", 123, "test", Role.READER, "test"));
        userDao.getAll().forEach(System.out::println);
        Assertions.assertEquals(2, userDao.getAll().size());
    }

    @Test
    void save() {
        userDao.save(new User(null, "test", "test", 123, "test", Role.ADMIN, "test"));
        userDao.save(new User(null, "test", "test", 123, "test", Role.ADMIN, "test"));
    }
}