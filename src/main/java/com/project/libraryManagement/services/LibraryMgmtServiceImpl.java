package com.project.libraryManagement.services;

import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.repositories.BooksRepo;
import com.project.libraryManagement.repositories.UsersWithBooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryMgmtServiceImpl implements LibraryMgmtService{

    @Autowired
    private UsersWithBooksRepo usersWithBooksRepo;

    @Autowired
    BooksRepo booksRepo;

    @Override
    public String saveUsersWithBooks(UsersWithBooks usersWithBooks) {
        List<UsersWithBooks> usersWithBooksList = usersWithBooksRepo.findByUserNameIgnoreCase(usersWithBooks.getUserName());
        Optional<Books> booksOptional = booksRepo.findById(usersWithBooks.getBookId());

        if(usersWithBooksList.size() < 2){
            if(booksOptional.isPresent() && booksOptional.get().getStockAvailable() > 0){
                if((0 == usersWithBooksList.size() || !usersWithBooks.getBookId().equals(usersWithBooksList.get(0).getBookId()))
                    && subscribeBook(usersWithBooks.getBookId())){
                    usersWithBooksRepo.save(usersWithBooks);
                    return "Book borrowed successfully!";
                }
                else
                    return "You have already borrowed this book!";
            }
            else
                return "Stock of this book is unavailable!";
        }
        else
            return "You have reached the borrow-limit!";
    }

    @Override
    public List<UsersWithBooks> fetchUsersWithBooks() {
        return usersWithBooksRepo.findAll();
    }

    @Override
    public UsersWithBooks fetchUsersWithBooksById(Long id) {
        if(usersWithBooksRepo.findById(id).isPresent())
            return usersWithBooksRepo.findById(id).get();
        return null;
    }

    @Override
    public String deleteUsersWithBooksById(Long id) {
        UsersWithBooks usersWithBooks = fetchUsersWithBooksById(id);
        if(null != usersWithBooks && unSubscribeBook(usersWithBooks.getBookId())){
            usersWithBooksRepo.deleteById(id);
            return "Book returned successfully!";
        }
        return "You have not borrowed this book!";
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
    public boolean subscribeBook(Long bookId){
        Optional<Books> booksOptional = booksRepo.findById(bookId);
        if(booksOptional.isPresent() && booksOptional.get().getStockAvailable() > 0) {
            Books booksDB = booksOptional.get();
            booksDB.setStockAvailable(booksDB.getStockAvailable() - 1);
            booksRepo.save(booksDB);
        }
        else
            return false;

        return true;
    }

    @Override
    public boolean unSubscribeBook(Long bookId){
        Optional<Books> booksOptional = booksRepo.findById(bookId);
        if(booksOptional.isPresent()) {
            Books booksDB = booksOptional.get();
            booksDB.setStockAvailable(booksDB.getStockAvailable() + 1);
            booksRepo.save(booksDB);
        }
        else
            return false;

        return true;
    }
}
