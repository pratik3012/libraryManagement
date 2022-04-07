package com.project.libraryManagement.repositories;

import com.project.libraryManagement.entities.UsersWithBooks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsersWithBooksRepoTest {

    @Autowired
    UsersWithBooksRepo usersWithBooksRepo;

    @BeforeEach
    void setUp() {
        UsersWithBooks usersWithBooks = UsersWithBooks.builder()
                .subscriptionId(1L)
                .userName("Pratik")
                .bookId(101L)
                .build();
        usersWithBooksRepo.save(usersWithBooks);
    }

    @Test
    @DisplayName("Get Data based on Valid User Name")
    void findByUsername_thenReturnUserWithBooks() {
        UsersWithBooks usersWithBooks = usersWithBooksRepo.findByUserNameIgnoreCase("Pratik").get(0);
        assertEquals(usersWithBooks.getUserName(), "Pratik");
    }
}