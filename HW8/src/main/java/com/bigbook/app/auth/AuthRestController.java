package com.bigbook.app.auth;

import com.bigbook.app.auth.dto.AuthRequestDto;
import com.bigbook.app.auth.dto.RedirectResponseDto;
import com.bigbook.app.auth.dto.RegisterRequestDto;
import com.bigbook.app.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public Response<RedirectResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto, HttpServletResponse servletResponse) {
        RedirectResponseDto responseDto = authService.login(authRequestDto, servletResponse);
        return Response.success(responseDto);
    }

    @PostMapping("/register")
    public Response<RedirectResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return Response.success(authService.register(registerRequestDto));
    }

}
