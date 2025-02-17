package app.web;

import app.story.service.StoryService;
import app.user.service.dto.LoggedInUserViewServiceModel;
import app.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Slf4j
@Controller
public class IndexController {

    private final UserService userService;
    private final StoryService storyService;

    @Autowired
    public IndexController(UserService userService, StoryService storyService) {
        this.userService = userService;
        this.storyService = storyService;
    }

    @GetMapping("/")
    public String getIndexPage(HttpSession session) {

        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(HttpSession session, Model model) {
        LoggedInUserViewServiceModel loggedIn = userService.getLoggedInUserModel((UUID) session.getAttribute("user_id"));
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("allStories", storyService.getAllVisibleStories());

        return "home";
    }



}
