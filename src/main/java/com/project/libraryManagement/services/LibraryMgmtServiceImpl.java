package com.project.libraryManagement.services;

import com.project.libraryManagement.Utils.Application_Constants;
import com.project.libraryManagement.entities.Books;
import com.project.libraryManagement.entities.UsersWithBooks;
import com.project.libraryManagement.error.SubscriptionNotFoundException;
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
                    return Application_Constants.BOOK_BORROWED;
                }
                else
                    return Application_Constants.BOOK_ALREADY_BORROWED;
            }
            else
                return Application_Constants.STOCK_UNAVAILABLE;
        }
        else
            return Application_Constants.LIMIT_REACHED;
    }

    @Override
    public List<UsersWithBooks> fetchUsersWithBooks() {
        return usersWithBooksRepo.findAll();
    }

    @Override
    public UsersWithBooks fetchUsersWithBooksById(Long id) throws SubscriptionNotFoundException {
        if(!usersWithBooksRepo.findById(id).isPresent())
            throw new SubscriptionNotFoundException(Application_Constants.INVALID_SUB_ID);
        return usersWithBooksRepo.findById(id).get();
    }

    @Override
    public String deleteUsersWithBooksById(Long id) throws SubscriptionNotFoundException {
        UsersWithBooks usersWithBooks = fetchUsersWithBooksById(id);
        if(null != usersWithBooks && unSubscribeBook(usersWithBooks.getBookId())){
            usersWithBooksRepo.deleteById(id);
            return Application_Constants.BOOK_RETURNED;
        }
        return Application_Constants.BOOK_NOT_BORROWED;
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
                return Application_Constants.BOOK_ID_EXISTS;
            }
            if(books.getStockAvailable() < 0){
                return Application_Constants.INVALID_STOCK_VALUE;
            }
            books.setStockAvailable(books.getStockAvailable() + booksDB.getStockAvailable());
            booksRepo.save(books);
            return Application_Constants.BOOKS_UPDATED;
        }
        else if(books.getStockAvailable() > 0){
            booksRepo.save(books);
            return Application_Constants.BOOKS_INSERTED;
        }
        else
            return Application_Constants.INVALID_STOCK_VALUE;
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
