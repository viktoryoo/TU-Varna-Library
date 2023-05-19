package com.example.library.dao;

import com.example.library.entities.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookDaoTest {
    private BookDao bookDao = new BookDao();

    @Test
    void getAll() {
        bookDao.save(new Book(null, "test", "author", "roman", "publisher", 1999, true, null, null));
        bookDao.save(new Book(null, "test", "author", "roman", "publisher", 1999, true, null, null));
        bookDao.getAll().forEach(System.out::println);
        Assertions.assertEquals(2, bookDao.getAll().size());
    }

    @Test
    void save() {
        var book1 = new Book(null, "test", "author", "roman", "publisher", 1999, true, null, null);
        bookDao.save(book1);
        Assertions.assertNotNull(book1.getId());
    }
}