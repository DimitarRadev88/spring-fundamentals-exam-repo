package app.web;

import app.story.service.StoryService;
import app.web.dto.StoryNewBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/new")
    public String getStoryAddPage(Model model) {
        if (!model.containsAttribute("newStory")) {
            model.addAttribute("newStory", new StoryNewBindingModel());
        }

        return "add-story";
    }

    @PostMapping("/new")
    public ModelAndView addNewStory(
            ModelAndView modelAndView,
            @Valid @ModelAttribute("newStory") StoryNewBindingModel newStory,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newStory", newStory);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newStory", bindingResult);
            modelAndView.setViewName("redirect:/stories/new");
        } else {
            storyService.doAdd(newStory,(UUID) session.getAttribute("user_id"));
            modelAndView.setViewName("redirect:/home");
        }

        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public String deleteStory(@PathVariable UUID id) {
        storyService.delete(id);

        return "redirect:/home";
    }

    @PatchMapping("/{id}/visibility")
    public String shareStory(@PathVariable UUID id) {
        storyService.makeStoryVisible(id);

        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String getReadStoryPage(@PathVariable UUID id, Model model) {
        model.addAttribute("story", storyService.getReadStoryViewModel(id));

        return "story";
    }

}
