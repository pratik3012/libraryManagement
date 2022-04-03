package com.project.libraryManagement.services;

import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;

import java.util.List;

public interface LibraryMgmtService {
    String saveUsersWithBooks(UsersWithBooks usersWithBooks);

    List<UsersWithBooks> fetchUsersWithBooks();

    UsersWithBooks fetchUsersWithBooksById(long id);

    void deleteUsersWithBooksById(Long id);

    UsersWithBooks updateUsersWithBooks(long id, UsersWithBooks usersWithBooks);

    List<UsersWithBooks> fetchUsersWithBooksByName(String name);

    List<Books> fetchBooks();

    String saveBooks(Books books);

    boolean subscribeBook(long bookId);
}
