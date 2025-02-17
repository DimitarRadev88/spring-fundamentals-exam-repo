package app.story.service.dto;

import app.story.model.Kind;

import java.util.UUID;

public record MyStoryViewServiceModel(UUID id, String title, String description, Kind kind) {
}
