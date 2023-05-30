package com.example.library.helpers;

import com.example.library.dao.BookDao;
import com.example.library.dao.BorrowedBookDao;
import com.example.library.dao.UserDao;

public class ServiceLocator {

    private static ServiceLocator locator;

    private BookDao bookDao = new BookDao();
    private BorrowedBookDao borrowedBookDao = new BorrowedBookDao();
    private UserDao userDao = new UserDao();

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        if (locator == null) {
            locator = new ServiceLocator();
        }
        return locator;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BorrowedBookDao getBorrowedBookDao() {
        return borrowedBookDao;
    }

    public void setBorrowedBookDao(BorrowedBookDao borrowedBookDao) {
        this.borrowedBookDao = borrowedBookDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}