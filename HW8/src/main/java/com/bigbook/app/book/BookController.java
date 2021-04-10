package com.bigbook.app.book;

import com.bigbook.app.auth.jwt.JwtTokenService;
import com.bigbook.app.auth.permissions.Permission;
import com.bigbook.app.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        boolean canAddNewBook = JwtTokenService.getContextUser().getPermissions().stream()
                .anyMatch(permissionEntity -> permissionEntity.getPermission().equals(Permission.ADD_NEW_BOOK));
        model.addAttribute("canAddNewBook", canAddNewBook);
        return "books";
    }

    @GetMapping("/favourites")
    public String getFavourites(Model model) {
        UserEntity contextUser = JwtTokenService.getContextUser();
        model.addAttribute("books", contextUser.getFavoriteBooks());
        return "favourites";
    }

    @GetMapping("/{isbn}")
    public String getBookInfo(@PathVariable("isbn") String isbn, Model model) {
        bookService.getBookInfo(isbn, model);
        return "bookPage";
    }

}
