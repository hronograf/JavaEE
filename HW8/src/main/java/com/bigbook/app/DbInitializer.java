package com.bigbook.app;

import com.bigbook.app.auth.permissions.Permission;
import com.bigbook.app.auth.permissions.PermissionEntity;
import com.bigbook.app.auth.permissions.PermissionRepository;
import com.bigbook.app.book.BookEntity;
import com.bigbook.app.book.BookRepository;
import com.bigbook.app.user.UserEntity;
import com.bigbook.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DbInitializer {
    private final BookRepository bookRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    private final int INIT_BOOK_AMOUNT = 20;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for(int i = 0; i < INIT_BOOK_AMOUNT; i++) {
            bookRepository.saveAndFlush(
                    BookEntity.builder()
                            .isbn("0-00-" + i)
                            .title("initBook_" + (char)(97 + i))
                            .author("initAuthor_" + (char)(97 + i))
                            .build());
        }

        PermissionEntity addNewBookPermission = new PermissionEntity();
        addNewBookPermission.setPermission(Permission.ADD_NEW_BOOK);
        permissionRepository.saveAndFlush(addNewBookPermission);

        userRepository.saveAndFlush(
                UserEntity.builder()
                        .username("user")
                        .password("$2y$12$4Nquq9ZNFW78ONR3DfXFJeyCA27KJ1A.D2jxUGwyUSOhG8SBHNS0e") // user
                        .build()
        );
        userRepository.saveAndFlush(
                UserEntity.builder()
                        .username("admin")
                        .password("$2y$12$3Qe3y0Wunu0bcomf8vjsM.hIDelZuQ3cEAvv6Ejp34wOjXUs16kKe") // admin
                        .permissions(Set.of(addNewBookPermission))
                        .build()
        );
    }

}
