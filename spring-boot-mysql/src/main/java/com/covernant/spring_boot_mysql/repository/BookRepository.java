package com.covernant.spring_boot_mysql.repository;

import com.covernant.spring_boot_mysql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String name);
}
