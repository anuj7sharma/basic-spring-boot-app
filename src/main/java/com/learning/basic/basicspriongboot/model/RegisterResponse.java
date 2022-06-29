package com.learning.basic.basicspriongboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private final String firstName;
    private final String lastName;
    private final String email;
}
