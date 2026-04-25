package com.covernant.spring_boot_mysql.model.request;

import lombok.Data;

import java.util.List;

@Data
public class BookLendRequest {
    private List<Long> bookIds;
    private Long memberId;
}
