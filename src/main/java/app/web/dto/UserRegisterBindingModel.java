package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterBindingModel(
        @NotBlank(message = "Username cannot be null or empty!")
        @Size(min = 4, message = "Username must be at least 4 characters!")
        String username,
        @NotBlank(message = "Password cannot be null or empty!")
        @Size(min = 4, message = "Password must be at least 4 characters!")
        String password
) {

    public UserRegisterBindingModel() {
        this(null, null);
    }

}
