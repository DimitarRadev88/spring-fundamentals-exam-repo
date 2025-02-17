package app.web.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record UserEditProfileBindingModel(
        @Length(min = 2, max = 20, message = "First name length must be between 2 and 20 characters!")
        String firstName,
        @Length(min = 2, max = 20, message = "Last name length must be between 2 and 20 characters!")
        String lastName,
        @Email(message = "Enter valid email address")
        String email,
        @URL(message = "Enter valid URL link")
        String profilePictureUrl
) {

}
