package com.covernant.spring_boot_mysql.service;

import com.covernant.spring_boot_mysql.model.Book;
import com.covernant.spring_boot_mysql.repository.AuthorRepository;
import com.covernant.spring_boot_mysql.repository.BookRepository;
import com.covernant.spring_boot_mysql.repository.LendRepository;
import com.covernant.spring_boot_mysql.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    
}
