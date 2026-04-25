package com.covernant.spring_boot_mysql.model.request;

import lombok.Data;

@Data
public class AuthorCreationRequest {
    private String firstName;
    private String lastName;
}
