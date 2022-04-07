package com.project.libraryManagement.services;

import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.error.SubscriptionNotFoundException;

import java.util.List;

public interface LibraryMgmtService {
    String saveUsersWithBooks(UsersWithBooks usersWithBooks);

    List<UsersWithBooks> fetchUsersWithBooks();

    UsersWithBooks fetchUsersWithBooksById(Long id) throws SubscriptionNotFoundException;

    String deleteUsersWithBooksById(Long id) throws SubscriptionNotFoundException;

    List<UsersWithBooks> fetchUsersWithBooksByName(String name);

    List<Books> fetchBooks();

    String saveBooks(Books books);

    boolean subscribeBook(Long bookId);

    boolean unSubscribeBook(Long bookId);
}
