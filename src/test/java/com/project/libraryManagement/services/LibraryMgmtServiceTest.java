package com.project.libraryManagement.services;

import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.repositories.UsersWithBooksRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryMgmtServiceTest {

    @Autowired
    LibraryMgmtService libraryMgmtService;

    @MockBean
    private UsersWithBooksRepo usersWithBooksRepo;

    @BeforeEach
    void setUp() {
        UsersWithBooks usersWithBooks1 = UsersWithBooks.builder()
                .subscriptionId(1L)
                .userName("Pratik")
                .bookId(101L)
                .build();

        UsersWithBooks usersWithBooks2 = UsersWithBooks.builder()
                .subscriptionId(2L)
                .userName("Pratik")
                .bookId(102L)
                .build();

        UsersWithBooks usersWithBooks3 = UsersWithBooks.builder()
                .subscriptionId(3L)
                .userName("Pooja")
                .bookId(103L)
                .build();

        List<UsersWithBooks> usersWithBooksList = new ArrayList<>();
        usersWithBooksList.add(usersWithBooks1);
        usersWithBooksList.add(usersWithBooks2);
        usersWithBooksList.add(usersWithBooks3);

        Mockito.when(usersWithBooksRepo.findByUserNameIgnoreCase("Pratik")).thenReturn(usersWithBooksList);
    }

    @Test
    @DisplayName("Get Data Based on Valid User Name")
    public void whenValidUserName_thenUserWithBooksFound(){
        String userName = "Pratik";
        List<UsersWithBooks> foundList = libraryMgmtService.fetchUsersWithBooksByName(userName);
        for(int i = 0; i < foundList.size(); i++)
            assertEquals(userName, foundList.get(i).getUserName());
    }
}