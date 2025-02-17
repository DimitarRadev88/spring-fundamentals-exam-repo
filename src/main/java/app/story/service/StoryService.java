package app.story.service;

import app.story.service.dto.ReadStoryViewServiceModel;
import app.story.service.dto.VisibleStoryViewServiceModel;
import app.web.dto.StoryNewBindingModel;

import java.util.List;
import java.util.UUID;

public interface StoryService {
    void doAdd(StoryNewBindingModel newStory, UUID userId);

    void delete(UUID id);

    void makeStoryVisible(UUID id);

    List<VisibleStoryViewServiceModel> getAllVisibleStories();

    ReadStoryViewServiceModel getReadStoryViewModel(UUID id);
}
