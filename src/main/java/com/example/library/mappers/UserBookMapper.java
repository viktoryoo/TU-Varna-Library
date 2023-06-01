package com.example.library.mappers;

import com.example.library.dto.UserBook;
import com.example.library.entities.Book;
import com.example.library.entities.BorrowedBook;

public class UserBookMapper {

    public static UserBook toUserBook(Book book, BorrowedBook borrowedBook) {
        UserBook userBook = new UserBook();
        userBook.setId(book.getId());
        userBook.setInventoryNumber(book.getInventoryNumber());
        userBook.setTitle(book.getTitle());
        userBook.setAuthor(book.getAuthor());
        userBook.setGenre(book.getGenre());
        userBook.setPublisher(book.getPublisher());
        userBook.setYearOfPublication(book.getYearOfPublication());
        userBook.setLoanDate(borrowedBook.getLoanDate());
        userBook.setReturnDate(borrowedBook.getReturnDate());
        return userBook;
    }
}
