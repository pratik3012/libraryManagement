package com.project.libraryManagement.controller;

import com.project.libraryManagement.Utils.Application_Constants;
import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.services.LibraryMgmtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(LibraryMgmtController.class)
class LibraryMgmtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryMgmtServiceImpl libraryMgmtService;

    private Books books;
    private UsersWithBooks usersWithBooks;
    private List<Books> booksList = new ArrayList<>();
    private List<UsersWithBooks> usersWithBooksList = new ArrayList<>();
    private String userName;

    @BeforeEach
    void setUp() {
        books = Books.builder()
                .bookId(101L)
                .bookName("Da Vinci Code")
                .stockAvailable(3)
                .build();

        booksList.add(books);

        usersWithBooks = UsersWithBooks.builder()
                .subscriptionId(1L)
                .bookId(101L)
                .userName("Pratik")
                .build();

        usersWithBooksList.add(usersWithBooks);

        userName = "Pratik";
    }

    @Test
    void saveBooks() throws Exception {
        Mockito.when(libraryMgmtService.saveBooks(books)).thenReturn(Application_Constants.BOOKS_INSERTED);

        mockMvc.perform(MockMvcRequestBuilders.post("/saveBooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"bookId\":\"101\",\n" +
                        "    \"bookName\":\"Da Vinci Code\",\n" +
                        "    \"stockAvailable\":\"1\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void fetchBooks() throws Exception {
        Mockito.when(libraryMgmtService.fetchBooks())
                .thenReturn(booksList);

        mockMvc.perform(MockMvcRequestBuilders.get("/fetchBooks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookName").value(booksList.get(0).getBookName()));
    }

    @Test
    void saveUsersWithBooks() throws Exception {
        Mockito.when(libraryMgmtService.saveUsersWithBooks(usersWithBooks))
                .thenReturn(Application_Constants.BOOK_BORROWED);

        mockMvc.perform(MockMvcRequestBuilders.post("/saveUsersWithBooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"userName\": \"Pratik\",\n" +
                        "    \"bookId\": \"101\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void fetchUsersWithBooksByName() throws Exception {
        Mockito.when(libraryMgmtService.fetchUsersWithBooksByName(userName))
                .thenReturn(usersWithBooksList);

        mockMvc.perform(MockMvcRequestBuilders.get("/fetchUsersWithBooks/userName/Pratik")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value(usersWithBooksList.get(0).getUserName()));
    }

    @Test
    void deleteUsersWithBooksById() throws Exception {
        Mockito.when(libraryMgmtService.deleteUsersWithBooksById(usersWithBooks.getSubscriptionId()))
                .thenReturn(Application_Constants.BOOK_RETURNED);

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUsersWithBooks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}