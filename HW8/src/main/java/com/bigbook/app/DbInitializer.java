package com.bigbook.app;

import com.bigbook.app.auth.permission.Permission;
import com.bigbook.app.auth.permission.PermissionEntity;
import com.bigbook.app.auth.permission.PermissionRepository;
import com.bigbook.app.book.BookEntity;
import com.bigbook.app.book.BookRepository;
import com.bigbook.app.user.UserEntity;
import com.bigbook.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInitializer {
    private final BookRepository bookRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    private final int INIT_BOOK_AMOUNT = 10;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for(int i = 0; i < INIT_BOOK_AMOUNT; i++) {
            bookRepository.saveAndFlush(
                    BookEntity.builder()
                            .isbn("0-00-" + i)
                            .title("InitBook_" + i)
                            .author("InitAuthor_" + i)
                            .build());
        }

        PermissionEntity permission = PermissionEntity.from(Permission.ADD_BOOK_TO_FAVORITE);

        permissionRepository.saveAndFlush(permission);

        userRepository.saveAndFlush(
                UserEntity.builder()
                        .username("user")
                        .password("$2y$12$4Nquq9ZNFW78ONR3DfXFJeyCA27KJ1A.D2jxUGwyUSOhG8SBHNS0e") // user
                        .permissions(List.of(permission))
                        .build()
        );
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
