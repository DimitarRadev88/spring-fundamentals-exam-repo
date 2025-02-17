package app.user.service.dto;

import app.story.model.Kind;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link app.story.model.Story}
 */
public record StoryDto(UUID id, String title, String description, Kind kind, LocalDate date, LocalDate addedOn,
                       Boolean isVisible) implements Serializable {
}