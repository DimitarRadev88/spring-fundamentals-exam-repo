package app.story.service.dto;

import java.time.LocalDate;

public record ReadStoryViewServiceModel(
        String title,
        String description,
        String addedBy,
        LocalDate publishedOn
) {
}
