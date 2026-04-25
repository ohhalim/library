package com.covernant.spring_boot_mysql.service;

import com.covernant.spring_boot_mysql.model.Author;
import com.covernant.spring_boot_mysql.model.Book;
import com.covernant.spring_boot_mysql.model.Lend;
import com.covernant.spring_boot_mysql.model.LendStatus;
import com.covernant.spring_boot_mysql.model.Member;
import com.covernant.spring_boot_mysql.model.MemberStatus;
import com.covernant.spring_boot_mysql.model.request.AuthorCreationRequest;
import com.covernant.spring_boot_mysql.model.request.BookCreationRequest;
import com.covernant.spring_boot_mysql.model.request.BookLendRequest;
import com.covernant.spring_boot_mysql.model.request.MemberCreationRequest;
import com.covernant.spring_boot_mysql.repository.AuthorRepository;
import com.covernant.spring_boot_mysql.repository.BookRepository;
import com.covernant.spring_boot_mysql.repository.LendRepository;
import com.covernant.spring_boot_mysql.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    public Book createBook(BookCreationRequest book) { // 요청으로 들어온 authorID 저자 엔티티 조회
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        if (!author.isPresent()) {
            throw new EntityNotFoundException(
                    "Author not found");
        }
        Book bookToCreate = new Book(); // 요청 DTO를 실제 저장할 Book 엔티티로 변환
        BeanUtils.copyProperties(book, bookToCreate); //  DTO와 엔티티에서 이름이 같은 필드 name, isbn을 복사
        bookToCreate.setAuthor(author.get());// dto에는 authorId만 있으므로 조회한 author 엔티티를 직접 연결, 연관관계를 완성
        return bookRepository.save(bookToCreate); // 완성된 Book 엔티티를 DB에 저장하고 저장결과를 반환
    }
    public void deleteBook(Long id) {
//        Optional<Book> book = bookRepository.findById(id);
//        if (book.isPresent()) {
//            bookRepository.delete(book.get());
//            return;
//        }
//        else throw new EntityNotFoundException(
//                "Cannot delete book with id: " + id);

        // 삭제할 책 조회, 없으면 예외 발생
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find any under given id: " + id));
        // 조회된 책 삭제
        bookRepository.delete(book);
    }

    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        member.setStatus(MemberStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, MemberCreationRequest request) {
        // 전달받은 id로 기존 회원을 조회 한다
        // 회원이 없으면 entitynotfoundexception을 발생시킨다
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find any under given id: " + id));
        // 요청 DTO에 들어온 firstname 값으로 기존 회원의 이름을  수정한다
        member.setFirstName(request.getFirstName());
        // 요청 DTO에 들어온 lastname 값으로 기존 회원의 성을 수정한다
        member.setLastName(request.getLastName());
        // 수정된 회원 엔티티를 DB에 다시 저장하고 그결과를 반환한다
        return memberRepository.save(member);
    }

    public Author createAuthor(AuthorCreationRequest request) {
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }

    public List<String> lendABook(BookLendRequest request) {
        List<String> booksApprovedToBorrow = new ArrayList<>();

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Member not present in the database"));

        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new RuntimeException("User is not active to proceed a lending.");
        }

        request.getBookIds().forEach(bookId -> {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cant find any book under given ID"));

            Optional<Lend> borrowedBook =
                    lendRepository.findByBookAndStatus(book, LendStatus.BURROWED);

            if (borrowedBook.isEmpty()) {
                booksApprovedToBorrow.add(book.getName());

                Lend lend = new Lend();
                lend.setMember(member);
                lend.setBook(book);
                lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));

                lendRepository.save(lend);
            }
        });

        return booksApprovedToBorrow;
    }
}
