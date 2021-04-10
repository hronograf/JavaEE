package com.bigbook.app.book.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateBookRequestDto {
    @NotBlank
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)(?=^\\d.*)(?=.*\\d$)[\\d-]+$")
    private String isbn;
    @NotBlank
    @Pattern(regexp = "^(?!^\\s.*)(?!.*\\s$)[A-Za-z0-9 ]{1,30}$")
    private String title;
    @NotBlank
    @Pattern(regexp = "^(?!^\\s.*)(?!.*\\s$)[A-Za-z ]{1,20}$")
    private String author;
}
