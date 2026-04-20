package com.covernant.spring_boot_mysql.repository;

import com.covernant.spring_boot_mysql.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
