package com.covernant.spring_boot_mysql.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "lend")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant startOn;
    private Instant dueOn;

    @Enumerated(EnumType.STRING)
    private LendStatus status;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;
}
