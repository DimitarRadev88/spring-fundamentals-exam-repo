package app.user.service.dto;

import app.story.service.dto.MyStoryViewServiceModel;

import java.util.List;
import java.util.UUID;

public record LoggedInUserViewServiceModel(UUID id, String username, String firstName, String email,
                                           String profilePictureUrl, List<MyStoryViewServiceModel> stories) {
}