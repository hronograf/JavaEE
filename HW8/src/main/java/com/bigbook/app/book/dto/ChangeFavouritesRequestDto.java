package com.bigbook.app.book.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeFavouritesRequestDto {
    @NotBlank
    private String isbn;
}
