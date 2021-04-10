package com.bigbook.app.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SearchBookResponseDto {
    private List<BookInfoDto> books;
    private int canBeShown;
}
