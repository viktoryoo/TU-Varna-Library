package com.example.library.dao;

import com.example.library.entities.Book;
import com.example.library.helpers.ServiceLocator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookDaoTest {
    private BookDao bookDao = ServiceLocator.getInstance().getBookDao();

    @Test
    void getAll() {
        bookDao.save(new Book(null, "test", "author", "roman", "publisher", 1999, true, false,  "1000/1231", 2, null, null));
        bookDao.save(new Book(null, "test", "author", "roman", "publisher", 1999, true, false,  "1000/1231", 2, null, null));
        bookDao.getAll().forEach(System.out::println);
        Assertions.assertEquals(2, bookDao.getAll().size());
    }

    @Test
    void save() {
        var book1 = new Book(null, "test", "author", "roman", "publisher", 1999, true, false,  "1000/1231", 2, null, null);
        bookDao.save(book1);
        Assertions.assertNotNull(book1.getId());
    }
}