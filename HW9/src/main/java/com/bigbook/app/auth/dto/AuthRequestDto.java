package com.bigbook.app.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequestDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
