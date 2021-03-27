package com.bigbook.app.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByTitleIgnoreCaseContainsAndIsbnContains(String title, String isbn);
}
