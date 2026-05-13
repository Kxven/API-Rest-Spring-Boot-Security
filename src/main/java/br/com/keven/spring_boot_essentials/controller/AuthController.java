package br.com.keven.spring_boot_essentials.controller;

import br.com.keven.spring_boot_essentials.dto.LoginRequesteDto;
import br.com.keven.spring_boot_essentials.dto.RegisterRequesteDto;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import br.com.keven.spring_boot_essentials.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequesteDto registerRequesteDto) throws BadRequestException {
        authenticationService.register(registerRequesteDto);
    }

    @PostMapping("/login")
    public void register(@RequestBody @Valid LoginRequesteDto loginRequesteDto) throws Exception {
        authenticationService.login(loginRequesteDto);
    }

}
