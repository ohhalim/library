package com.covernant.spring_boot_mysql.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING) // @Enumerated 자체를 생략하면 ORDINAL이 default
    // Enum을 DB에 문자열로 저장 (ORDINAL 쓰면 순서 바뀔 때 데이터 깨짐)    
    private MemberStatus status;
}
