package com.spring.rapifuzz.exam.controller;

import com.spring.rapifuzz.exam.dto.LoginRequest;
import com.spring.rapifuzz.exam.exception.BadRequestException;
import com.spring.rapifuzz.exam.exception.ResourceNotFoundException;
import com.spring.rapifuzz.exam.repo.UserRepository;
import com.spring.rapifuzz.exam.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController

public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    CookieCsrfTokenRepository cookieCsrfTokenRepository;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User authenticatedUser = (User) authentication.getPrincipal();

            com.spring.rapifuzz.exam.entity.User user = userService.getUserByName(loginRequest.getUsername());

            session.setAttribute("username", authenticatedUser.getUsername());
            session.setAttribute("userId", user.getUserId());
//            CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//            cookieCsrfTokenRepository.setSecure(true);
//            cookieCsrfTokenRepository.setCookieHttpOnly(true);
//            response.addCookie(new Cookie("XSRF-TOKEN", cookieCsrfTokenRepository.generateToken(request).getToken()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Login successful. Welcome, " + authenticatedUser.getUsername() + ". Your session ID is: " + session.getId());
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid username or password.");
        }
    }

    @GetMapping("/login/user")
    public ResponseEntity<com.spring.rapifuzz.exam.entity.User> getLoggedInUser(){
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("Logout successful: " + session.getId());
    }

    @PostMapping("/reset/password")
    @ResponseBody
    public ResponseEntity<com.spring.rapifuzz.exam.entity.User> resetPassword(@RequestBody com.spring.rapifuzz.exam.entity.User user) {
        com.spring.rapifuzz.exam.entity.User dbUser = userService.getUserByName(user.getUserName());
        return ResponseEntity
                .ok()
                .body(userService.updatePassword(user, dbUser));
    }
}
