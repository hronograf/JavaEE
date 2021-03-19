package com.bigbook.app.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Getter
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager entityManager;

    @Transactional
    public void saveBook(BookEntity book) {
        entityManager.persist(book);
    }

    public List<BookEntity> getAllBooks() {
        return entityManager.createQuery("FROM BookEntity", BookEntity.class).getResultList();
    }

    public BookEntity getBookByIsbn(String isbn) {
        return entityManager.createQuery("FROM BookEntity b WHERE b.isbn=:isbn", BookEntity.class)
                .setParameter("isbn", isbn).getSingleResult();
    }
}
