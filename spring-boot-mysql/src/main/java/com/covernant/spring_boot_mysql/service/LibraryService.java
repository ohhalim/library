package com.covernant.spring_boot_mysql.service;

import com.covernant.spring_boot_mysql.model.Author;
import com.covernant.spring_boot_mysql.model.Book;
import com.covernant.spring_boot_mysql.repository.AuthorRepository;
import com.covernant.spring_boot_mysql.repository.BookRepository;
import com.covernant.spring_boot_mysql.repository.LendRepository;
import com.covernant.spring_boot_mysql.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public Book readBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) { // Optional 안에 값이 있으면 true, 없으면 false 반환
            return book.get(); // 값이 존재할떄 꺼내서 사용
        }
        throw new EntityNotFoundException(
                "Cannot find book with id: " + id); // 없으면 예외처리
    }
    public List<Book> readBooks() {
        return bookRepository.findAll(); // repo에 있는 Book의 모든 엔티티 조회해서 모든 책 반환
    }
    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn); // Optional의 목적은 null 체크를 하려고 하는거      
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException(
                "Can't find any under given isbn: " + isbn);
    }
    
}
