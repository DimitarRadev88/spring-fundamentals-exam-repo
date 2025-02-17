package app.story.service.dto;


import java.time.LocalDate;
import java.util.UUID;

public record VisibleStoryViewServiceModel(UUID id, String title, LocalDate addedOn, String addedBy) {

}