package com.bigbook.app.book;


import com.bigbook.app.book.dto.CreateBookRequestDto;
import com.bigbook.app.user.UserEntity;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @ManyToMany(mappedBy = "favoriteBooks")
    private Set<UserEntity> likes;

    public BookEntity(CreateBookRequestDto requestDto) {
        this.isbn = requestDto.getIsbn();
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
    }

    public BookEntity(String isbn) {
        this.isbn = isbn;
    }

    public static Specification<BookEntity> titleIgnoreCaseContains(String title) {
        String lowercaseTitle = title.toLowerCase();
        return (book, cq, cb) -> cb.like(cb.lower(book.get("title")), "%" + lowercaseTitle + "%");
    }

    public static Specification<BookEntity> isbnContains(String isbn) {
        return (book, cq, cb) -> cb.like(book.get("isbn"), "%" + isbn + "%");
    }

}
