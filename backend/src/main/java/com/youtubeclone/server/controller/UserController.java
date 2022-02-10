package com.youtubeclone.server.controller;

import com.youtubeclone.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/register")
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        iUserService.registerUser(jwt.getTokenValue());
        return "User registred successfuly";
    }

    @GetMapping("/subscribe/{userId}")
    public boolean subscribeUser(@PathVariable("userId") String userId) {
        iUserService.subscribeUser(userId);
        return true;
    }

    @GetMapping("/unsubscribe/{userId}")
    public boolean unSubscribeUser(@PathVariable("userId") String userId) {
        iUserService.unSubscribeUser(userId);
        return true;
    }
}
