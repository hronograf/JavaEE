package kma.topic2.junit.service;

import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService    userService;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private UserValidator  userValidator;

    @Test
    void shouldCreateNewUser() {
        String login    = "duckD";
        String password = "12345";
        String fullName = "Donald Duck";

        NewUser newUser = NewUser.builder()
                .login(login)
                .password(password)
                .fullName(fullName)
                .build();

        userService.createNewUser(newUser);

        verify(userValidator).validateNewUser(eq(newUser));

        assertThat(userRepository.getUserByLogin(login))
                .returns(login, User::getLogin)
                .returns(password, User::getPassword)
                .returns(fullName, User::getFullName);
    }

    @Test
    void shouldGiveCreatedUser() {
        String login    = "duckD";
        String password = "12345";
        String fullName = "Donald Duck";

        NewUser newUser = NewUser.builder()
                .login(login)
                .password(password)
                .fullName(fullName)
                .build();

        userService.createNewUser(newUser);

        assertThat(userService.getUserByLogin(login))
                .returns(login, User::getLogin)
                .returns(password, User::getPassword)
                .returns(fullName, User::getFullName);
    }

}
