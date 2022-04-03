package com.project.libraryManagement.services;

import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.repositories.BooksRepo;
import com.project.libraryManagement.repositories.UsersWithBooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LibraryMgmtServiceImpl implements LibraryMgmtService{

    @Autowired
    private UsersWithBooksRepo usersWithBooksRepo;

    @Autowired
    BooksRepo booksRepo;

    @Override
    public String saveUsersWithBooks(UsersWithBooks usersWithBooks) {
        List<UsersWithBooks> usersWithBooksList = new ArrayList<>();
        usersWithBooksList = usersWithBooksRepo.findByUserNameIgnoreCase(usersWithBooks.getUserName());

        if(usersWithBooksList.size() < 2){
            if(subscribeBook(usersWithBooks.getBookId())){
                UsersWithBooks usersWithBooksDB = usersWithBooksRepo.findByBookId(usersWithBooks.getBookId());
                if(null == usersWithBooksDB){
                    usersWithBooksRepo.save(usersWithBooks);
                    return "Subscribed successfully!";
                }
                else
                    return "You have already subscribed this book!";
            }
            else
                return "Stock of this book is unavailable!";
        }
        else
            return "You have reached the subscription limit!";
    }

    @Override
    public List<UsersWithBooks> fetchUsersWithBooks() {
        return usersWithBooksRepo.findAll();
    }

    @Override
    public UsersWithBooks fetchUsersWithBooksById(long id) {
        return usersWithBooksRepo.findById(id).get();
    }

    @Override
    public void deleteUsersWithBooksById(Long id) {
        usersWithBooksRepo.deleteByBookId(id);
    }

    @Override
    public UsersWithBooks updateUsersWithBooks(long id, UsersWithBooks usersWithBooks) {
        UsersWithBooks usersWithBooksDB = usersWithBooksRepo.findById(id).get();

        if(Objects.nonNull(usersWithBooks.getBookId()) && !"".equals(usersWithBooks.getBookId()))
            usersWithBooksDB.setBookId(usersWithBooks.getBookId());
        if(Objects.nonNull(usersWithBooks.getUserName()) && !"".equals(usersWithBooks.getUserName()))
            usersWithBooksDB.setUserName(usersWithBooks.getUserName());

        return usersWithBooksRepo.save(usersWithBooksDB);
    }

    @Override
    public List<UsersWithBooks> fetchUsersWithBooksByName(String name) {
        return usersWithBooksRepo.findByUserNameIgnoreCase(name);
    }

    @Override
    public List<Books> fetchBooks(){
        return booksRepo.findAll();
    }

    @Override
    public String saveBooks(Books books){
        Optional<Books> booksOptional = booksRepo.findById(books.getBookId());
        if(booksOptional.isPresent()) {
            Books booksDB = booksOptional.get();
            if(!booksDB.getBookName().equalsIgnoreCase(books.getBookName())){
                return "Book Id already exists!";
            }
            if(books.getStockAvailable() < 0){
                return "Enter valid stock value!";
            }
            books.setStockAvailable(books.getStockAvailable() + booksDB.getStockAvailable());
            booksRepo.save(books);
            return "Book/s updated successfully!";
        }
        else if(books.getStockAvailable() > 0){
            booksRepo.save(books);
            return "Book/s inserted successfully!";
        }
        else
            return "Book-stock entered is invalid!";
    }


    @Override
    public boolean subscribeBook(long bookId){
        Optional<Books> booksOptional = booksRepo.findById(bookId);
        if(booksOptional.isPresent()) {
            Books booksDB = booksOptional.get();
            if(booksDB.getStockAvailable() > 0) {
                booksDB.setStockAvailable(booksDB.getStockAvailable() - 1);
                booksRepo.save(booksDB);
            }
        }
        else
            return false;

        return true;
    }
}
