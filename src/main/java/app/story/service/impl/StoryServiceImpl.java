package app.story.service.impl;

import app.story.model.Story;
import app.story.repository.StoryRepository;
import app.story.service.StoryService;
import app.story.service.dto.ReadStoryViewServiceModel;
import app.story.service.dto.VisibleStoryViewServiceModel;
import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.StoryNewBindingModel;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository, UserService userService) {
        this.storyRepository = storyRepository;
        this.userService = userService;
    }

    @Override
    public void doAdd(StoryNewBindingModel newStory, UUID userId) {
        User user = userService.getUser(userId);

        Story story = new Story();
        story.setAddedBy(user);
        story.setTitle(newStory.title());
        story.setDescription(newStory.description());
        story.setKind(newStory.kind());
        story.setDate(newStory.date());
        story.setAddedOn(LocalDate.now());
        story.setIsVisible(false);

        storyRepository.save(story);

        log.info("Story added: {}", story.getTitle());
    }

    @Override
    public void delete(UUID id) {
        if (!storyRepository.existsById(id)) {
            throw new RuntimeException("Story not found");
        }

        storyRepository.deleteById(id);
    }

    @Override
    public void makeStoryVisible(UUID id) {
        Story story = storyRepository.findById(id).orElseThrow(() -> new RuntimeException("Story not found"));

        story.setIsVisible(true);

        storyRepository.save(story);

        log.info("Story shared: {}", story.getTitle());
    }

    @Override
    @Transactional
    public List<VisibleStoryViewServiceModel> getAllVisibleStories() {
        return storyRepository
                .findAllByIsVisible(true)
                .map(story -> new VisibleStoryViewServiceModel(
                        story.getId(),
                        story.getTitle(),
                        story.getAddedOn(),
                        story.getAddedBy().getUsername()))
                .toList();
    }

    @Override
    @Transactional
    public ReadStoryViewServiceModel getReadStoryViewModel(UUID id) {
        return storyRepository.findById(id)
                .map(story -> new ReadStoryViewServiceModel(
                        story.getTitle(),
                        story.getDescription(),
                        story.getAddedBy().getUsername(),
                        story.getAddedOn()))
                .orElseThrow(() -> new RuntimeException("Story not found!"));
    }
}
