package com.covernant.spring_boot_mysql.repository;

import com.covernant.spring_boot_mysql.model.Book;
import com.covernant.spring_boot_mysql.model.Lend;
import com.covernant.spring_boot_mysql.model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
