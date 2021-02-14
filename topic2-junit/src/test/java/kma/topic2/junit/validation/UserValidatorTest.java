package kma.topic2.junit.validation;

import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserValidator userValidator;

    @Test
    void shouldPassValidation() {
        String login = "login";
        userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .password("pass")
                        .fullName("fullName")
                        .build()
        );

        verify(userRepository).isLoginExists(login);
    }

    @Test
    void shouldThrowException_whenLoginExists() {
        String login = "login";
        when(userRepository.isLoginExists(login)).thenReturn(true);

        assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .password("pass")
                        .fullName("fullName")
                        .build()
        ))
                .isInstanceOf(LoginExistsException.class);
    }

    @ParameterizedTest
    @MethodSource("testPasswordDataProvider")
    void shouldThrowException_whenPasswordInvalid(String password, List<String> errors) {
        assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .login("login")
                        .password(password)
                        .fullName("fullName")
                        .build()
        ))
                .isInstanceOfSatisfying(
                        ConstraintViolationException.class,
                        ex -> assertThat(ex.getErrors()).containsExactlyElementsOf(errors)
                );
    }

    private static Stream<Arguments> testPasswordDataProvider() {
        return Stream.of(
                Arguments.of("1", List.of("Password has invalid size")),
                Arguments.of("12345678", List.of("Password has invalid size")),
                Arguments.of( "123!", List.of("Password doesn't match regex")),
                Arguments.of("!", List.of("Password has invalid size", "Password doesn't match regex")),
                Arguments.of( "12345678!", List.of("Password has invalid size", "Password doesn't match regex"))
        );
    }

}