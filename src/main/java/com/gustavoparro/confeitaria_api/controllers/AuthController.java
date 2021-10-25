package com.gustavoparro.confeitaria_api.controllers;

import com.gustavoparro.confeitaria_api.dtos.TokenDTO;
import com.gustavoparro.confeitaria_api.forms.AuthUserForm;
import com.gustavoparro.confeitaria_api.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthUserForm userForm) {
        try {
            String token = tokenService.generateToken(authManager.authenticate
                    (new UsernamePasswordAuthenticationToken(userForm.getUsername(), userForm.getPassword())));

            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException exception) {
            exception.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }

}
