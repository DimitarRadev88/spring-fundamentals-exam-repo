package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginBindingModel(
        @NotBlank(message = "Username cannot be null or empty!")
        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters!")
        String username,
        @NotBlank(message = "Password cannot be null or empty!")
        @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters!")
        String password
) {

        public UserLoginBindingModel() {
                this(null, null);
        }

        public UserLoginBindingModel(String username) {
                this(username, null);
        }

}
