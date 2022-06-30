package basic.springboot.service;

import basic.springboot.entity.UserEntity;
import basic.springboot.exception.BadRequestException;
import basic.springboot.model.RegisterRequest;
import basic.springboot.model.RegisterResponse;
import basic.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }

    @Override
    public RegisterResponse register(@Valid RegisterRequest request) {
        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            logger.debug(String.format("User already exists with email %s", request.getEmail()));
            throw new BadRequestException("User already exists with this email");
        }
        UserEntity userEntity = new UserEntity(request.getFirstName(),
                request.getLastName(),
                request.getEmail());
        userRepository.save(userEntity);
        return new RegisterResponse(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail());
    }
}
