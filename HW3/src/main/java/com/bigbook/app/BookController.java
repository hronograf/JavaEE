package com.bigbook.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/books")
    public String allBooksPageGet(Model model) {
        model.addAttribute("books", bookRepository.getBooks());
        return "all_books";
    }

    @GetMapping("/books/create")
    public String createBookGet() {
        return "create_book";
    }

    @PostMapping("/books/create")
    public String createBookPost(@ModelAttribute BookModel bookModel) {
        bookRepository.saveBook(bookModel);
        return "redirect:/books";
    }

}
