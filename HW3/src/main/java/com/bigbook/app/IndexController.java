package com.bigbook.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BookRepository bookRepository;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("books", bookRepository.getBooks());
        return "index";
    }
}
