package app.user.service.impl;

import app.exception.DomainException;
import app.exception.ExceptionMessage;
import app.story.service.dto.MyStoryViewServiceModel;
import app.user.model.User;
import app.user.repository.UserRepository;
import app.user.service.UserService;
import app.user.service.dto.LoggedInUserViewServiceModel;
import app.web.dto.UserEditProfileBindingModel;
import app.web.dto.UserLoginBindingModel;
import app.web.dto.UserRegisterBindingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String doRegister(UserRegisterBindingModel userRegister) {
        if (userRepository.existsByUsername((userRegister.username()))) {
            throw new DomainException(ExceptionMessage.USER_ALREADY_EXISTS.getMessage());
        }

        User user = new User();
        user.setUsername(userRegister.username());
        user.setPassword(passwordEncoder.encode(userRegister.password()));

        userRepository.save(user);

        log.info("User {} registered successfully", user.getUsername());

        return user.getUsername();
    }

    @Override
    public UUID doLogin(UserLoginBindingModel userLogin) {
        User user = userRepository
                .findByUsername(userLogin.username())
                .orElseThrow(() -> new DomainException(ExceptionMessage.INVALID_USERNAME_OR_PASSWORD.getMessage()));

        if (!passwordEncoder.matches(userLogin.password(), user.getPassword())) {
            throw new DomainException(ExceptionMessage.INVALID_USERNAME_OR_PASSWORD.getMessage());
        }

        log.info("User {} logged in successfully", user.getUsername());

        return user.getId();
    }

    @Override
    public LoggedInUserViewServiceModel getLoggedInUserModel(UUID userId) {
        return userRepository.findById(userId).map(u -> new LoggedInUserViewServiceModel(
                        u.getId(),
                        u.getUsername(),
                        u.getFirstName(),
                        u.getEmail(),
                        u.getProfilePictureUrl(),
                        u.getStories()
                                .stream()
                                .map(s -> new MyStoryViewServiceModel(
                                        s.getId(),
                                        s.getTitle(),
                                        s.getDescription(),
                                        s.getKind())
                                )
                                .toList()
                )
        ).orElseThrow(() -> new DomainException(ExceptionMessage.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public UserEditProfileBindingModel getEditModel(UUID userId) {
        return userRepository.findById(userId)
                .map(u -> new UserEditProfileBindingModel(
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getProfilePictureUrl())
                )
                .orElseThrow(() -> new DomainException(ExceptionMessage.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public void doEdit(UUID id, UserEditProfileBindingModel profileEdit) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new DomainException(ExceptionMessage.USER_NOT_FOUND.getMessage()));

        user.setFirstName(profileEdit.firstName());
        user.setLastName(profileEdit.lastName());
        user.setEmail(profileEdit.email());
        user.setProfilePictureUrl(profileEdit.profilePictureUrl());


        userRepository.save(user);
        log.info("User {} edited successfully", user.getUsername());
    }

    @Override
    public User getUser(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new DomainException(ExceptionMessage.USER_NOT_FOUND.getMessage()));
    }

}
