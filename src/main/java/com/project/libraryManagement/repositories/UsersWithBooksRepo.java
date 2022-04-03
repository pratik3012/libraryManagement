package com.project.libraryManagement.repositories;

import com.project.libraryManagement.entities.UsersWithBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersWithBooksRepo extends JpaRepository<UsersWithBooks, Long> {
    List<UsersWithBooks> findByUserNameIgnoreCase(String name);
    UsersWithBooks findByBookId(long bookId);
    void deleteByBookId(Long id);
}