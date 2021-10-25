package com.gustavoparro.confeitaria_api.filters;

import com.gustavoparro.confeitaria_api.models.AppUser;
import com.gustavoparro.confeitaria_api.repositories.AppUserRepository;
import com.gustavoparro.confeitaria_api.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AppUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = this.getToken(request);

        if (tokenService.validateToken(token)) {
            this.authenticateAppUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring("Bearer".length());
    }

    private void authenticateAppUser(String token) {
        AppUser appUser = this.getAppUser(this.tokenService.getUsername(token));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(appUser, appUser.getPassword(), appUser.getAuthorities()));
    }

    private AppUser getAppUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

}
