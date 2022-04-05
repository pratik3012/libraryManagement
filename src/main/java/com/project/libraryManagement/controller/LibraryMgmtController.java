package com.project.libraryManagement.controller;

import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.services.LibraryMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LibraryMgmtController {

    @Autowired
    private LibraryMgmtService libraryMgmtService;

    @GetMapping("/")
    public String helloWorldLib(){
        return "Welcome to Planet of Books!!! :D";
    }

    @PostMapping("/saveBooks")  //1
    public String saveBooks(@RequestBody Books books){
        return libraryMgmtService.saveBooks(books);
    }

    @GetMapping("/fetchBooks")  //2
    public List<Books> fetchBooks(){
        return libraryMgmtService.fetchBooks();
    }

    @PostMapping("/saveUsersWithBooks") //3
    public String saveUsersWithBooks(@Valid @RequestBody UsersWithBooks usersWithBooks){
        return libraryMgmtService.saveUsersWithBooks(usersWithBooks);
    }

    @GetMapping("/fetchUsersWithBooks/userName/{name}") //4
    public List<UsersWithBooks> fetchUsersWithBooksByName(@PathVariable("name") String name){
        return libraryMgmtService.fetchUsersWithBooksByName(name);
    }

    @DeleteMapping("/deleteUsersWithBooks/{subscriptionId}")    //5
    public String deleteUsersWithBooksById(@PathVariable("subscriptionId") Long id){
        return libraryMgmtService.deleteUsersWithBooksById(id);
    }

    @GetMapping("/fetchUsersWithBooks")
    public List<UsersWithBooks> fetchUsersWithBooks(){
        return libraryMgmtService.fetchUsersWithBooks();
    }

    @GetMapping("/fetchUsersWithBooks/{id}")
    public UsersWithBooks fetchUsersWithBooksById(@PathVariable("id") Long id){
        return libraryMgmtService.fetchUsersWithBooksById(id);
    }

}