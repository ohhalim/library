package com.covernant.spring_boot_mysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference // 무한참조를 막기 위한 직렬화 제어 어노테이션  자식엔티티에서 부모로 가는 참조는 JSON 직렬화에서 제외
    private Author author;

    @JsonBackReference
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //mappedBy = "book": 연관관계의 주인은 자식 엔티티의 book 필드, FK는 자식 쪽에 있다| FetchType.LAZY: 필요할만 데이터 조회 | CascadeType.All: 부모 엔티티의 영속성 변경을 자식에도 전파 , 저장 수정 삭제 같은 작업이 연관 엔티티에 같이 정용
    private List<Lend> lends;
    
}
