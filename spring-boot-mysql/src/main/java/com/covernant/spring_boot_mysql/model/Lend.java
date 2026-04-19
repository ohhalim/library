package com.covernant.spring_boot_mysql.model;

import jakarta.persistence.*;

import java.time.Instant;

public class Lend {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private Instant startOn;
    private Instant dueOn;

    @Enumerated(EnumType.ORDINAL)
    private LendStatus status;

}
