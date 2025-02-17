package app.user.service;

import app.user.model.User;
import app.user.service.dto.LoggedInUserViewServiceModel;
import app.web.dto.UserEditProfileBindingModel;
import app.web.dto.UserLoginBindingModel;
import app.web.dto.UserRegisterBindingModel;

import java.util.UUID;

public interface UserService {
    String doRegister(UserRegisterBindingModel userRegister);

    UUID doLogin(UserLoginBindingModel userLogin);

    LoggedInUserViewServiceModel getLoggedInUserModel(UUID userId);

    UserEditProfileBindingModel getEditModel(UUID userId);

    void doEdit(UUID id, UserEditProfileBindingModel profileEdit);

    User getUser(UUID userId);
}
