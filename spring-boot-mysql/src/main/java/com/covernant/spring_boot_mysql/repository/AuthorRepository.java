package com.covernant.spring_boot_mysql.repository;

import com.covernant.spring_boot_mysql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
