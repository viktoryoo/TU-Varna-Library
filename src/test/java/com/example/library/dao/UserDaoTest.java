package com.example.library.dao;

import com.example.library.entities.Role;
import com.example.library.entities.User;
import com.example.library.helpers.ServiceLocator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserDaoTest {
    UserDao userDao = ServiceLocator.getInstance().getUserDao();

    @Test
    void getAll() {
        userDao.save(new User(null, "test", "test", 123, "test", Role.ADMIN, "test",true , LocalDateTime.now(),
            LocalDateTime.now()));
        userDao.save(new User(null, "test", "test", 123, "test", Role.READER, "test", true,  LocalDateTime.now(),
            LocalDateTime.now()));
        userDao.getAll().forEach(System.out::println);
        Assertions.assertEquals(2, userDao.getAll().size());
    }

    @Test
    void save() {
        userDao.save(new User(null, "test", "test", 123, "test", Role.ADMIN, "test", true, LocalDateTime.now(),
            LocalDateTime.now()));
        userDao.save(new User(null, "test", "test", 123, "test", Role.ADMIN, "test", true,  LocalDateTime.now(),
            LocalDateTime.now()));
    }
}