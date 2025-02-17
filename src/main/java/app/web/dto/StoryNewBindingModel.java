package app.web.dto;

import app.story.model.Kind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record StoryNewBindingModel(
        @NotBlank(message = "Title cannot be null or empty!")
        @Size(min = 5, max = 25, message = "Title length must be between 5 and 25 characters!")
        String title,
        @NotBlank(message = "Description cannot be null or empty!")
        @Size(min = 10, max = 1000, message = "Description length must be between 10 and 1000 characters!")
        String description,
        @NotNull(message = "you must select an encounter kind!")
        Kind kind,
        @NotNull(message = "must not be null")
        LocalDate date
) {
        public StoryNewBindingModel() {
                this(null, null, null, null);
        }
}
