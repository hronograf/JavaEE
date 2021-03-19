package com.bigbook.app;

import com.bigbook.app.book.BookEntity;
import com.bigbook.app.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BookRepository bookRepository;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("books", bookRepository.getAllBooks());
        return "index";
    }

    @GetMapping("/book/{isbn}")
    public String getBookInfo(@PathVariable("isbn") String isbn, Model model) {
        BookEntity bookEntity = bookRepository.getBookByIsbn(isbn);
        model.addAttribute("book", bookEntity);
        return "bookPage";
    }
}
