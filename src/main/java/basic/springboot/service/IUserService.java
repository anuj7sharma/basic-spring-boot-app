package basic.springboot.service;

import basic.springboot.model.RegisterRequest;
import basic.springboot.model.RegisterResponse;

public interface IUserService {
    RegisterResponse register(RegisterRequest request);
}
