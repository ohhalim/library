package com.covernant.spring_boot_mysql.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter // Lombok이 get/set 매서드 자동 생성
@Setter // Lombok이 get/set 매서드 자동 생성
@Entity  // 이 클래스가 db테이블과 매핑된다 jpa에 알림
@Table(name= "book") // 테이블 명 지정
public class Book {
    @Id // 이 필드가 Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 를 DB 가 AUTO_INCREMENT 자동 생성
    private Long id;
    private String name; // 컬럼들
    private String isbn;
}
