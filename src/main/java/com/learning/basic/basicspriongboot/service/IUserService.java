package com.learning.basic.basicspriongboot.service;

import com.learning.basic.basicspriongboot.model.RegisterRequest;
import com.learning.basic.basicspriongboot.model.RegisterResponse;

public interface IUserService {
    RegisterResponse register(RegisterRequest request);
}
