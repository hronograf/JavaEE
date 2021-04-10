package com.bigbook.app.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RegisterRequestDto {
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9]{1,20}$")
    private String username;
    @NotEmpty
    @Pattern(regexp = "^.{8,20}$")
    private String password;
}
