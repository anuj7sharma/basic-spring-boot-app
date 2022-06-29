package com.learning.basic.basicspriongboot.controller;

import com.learning.basic.basicspriongboot.model.RegisterRequest;
import com.learning.basic.basicspriongboot.model.RegisterResponse;
import com.learning.basic.basicspriongboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(userService.register(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Working fine", HttpStatus.OK);
    }
}
