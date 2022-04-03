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
        return "Hello from World Library!!! :D";
    }

    @PostMapping("/saveUsersWithBooks")
    public String saveUsersWithBooks(@Valid @RequestBody UsersWithBooks usersWithBooks){
        return libraryMgmtService.saveUsersWithBooks(usersWithBooks);
    }

    @GetMapping("/fetchUsersWithBooks")
    public List<UsersWithBooks> fetchUsersWithBooks(){
        return libraryMgmtService.fetchUsersWithBooks();
    }

    @GetMapping("/fetchUsersWithBooks/{id}")
    public UsersWithBooks fetchUsersWithBooksById(@PathVariable("id") Long id){
        return libraryMgmtService.fetchUsersWithBooksById(id);
    }

    /*@GetMapping("/fetchUsersWithBooks/userName/{name}")
    public UsersWithBooks fetchUsersWithBooksByName(@PathVariable("name") String name){
        return libraryMgmtService.fetchUsersWithBooksByName(name);
    }*/

    @DeleteMapping("/deleteUsersWithBooks/{id}")
    public String deleteUsersWithBooksById(@PathVariable("id") long id){
        libraryMgmtService.deleteUsersWithBooksById(id);
        return "Subscription removed successfully!";
    }

    @PutMapping("/updateUsersWithBooks/{id}")
    public UsersWithBooks updateUsersWithBooks(@PathVariable("id") long id,
                                               @RequestBody UsersWithBooks usersWithBooks){
        return libraryMgmtService.updateUsersWithBooks(id, usersWithBooks);
    }

    @GetMapping("/fetchBooks")
    public List<Books> fetchBooks(){
        return libraryMgmtService.fetchBooks();
    }

    @PostMapping("/saveBooks")
    public String saveBooks(@RequestBody Books books){
        return libraryMgmtService.saveBooks(books);
    }

}