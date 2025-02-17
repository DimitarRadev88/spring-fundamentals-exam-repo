package app.web;

import app.user.service.UserService;
import app.web.dto.UserEditProfileBindingModel;
import app.web.dto.UserLoginBindingModel;
import app.web.dto.UserRegisterBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    private String getRegisterPage(Model model) {
        if (!model.containsAttribute("userRegister")) {
            model.addAttribute("userRegister", new UserRegisterBindingModel());
        }

        return "register";
    }

    @PostMapping("/register")
    private ModelAndView registerUser(ModelAndView modelAndView,
                                      @Valid @ModelAttribute("userRegister") UserRegisterBindingModel userRegister,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister", bindingResult);
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            modelAndView.setViewName("redirect:/register");
        } else {
            String username = userService.doRegister(userRegister);
            UserLoginBindingModel userLogin = new UserLoginBindingModel(username);
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        if (!model.containsAttribute("userLogin")) {
            model.addAttribute("userLogin", new UserLoginBindingModel());
        }

        return "login";
    }

    @PostMapping("/login")
    private ModelAndView registerUser(ModelAndView modelAndView,
                                      @Valid @ModelAttribute("userLogin") UserLoginBindingModel userLogin,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      HttpSession session) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLogin", bindingResult);
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            modelAndView.setViewName("redirect:/login");
        } else {
            UUID id = userService.doLogin(userLogin);
            session.setAttribute("user_id", id);
            modelAndView.setViewName("redirect:/home");
        }

        return modelAndView;
    }

    @GetMapping("edit-profile")
    public String getEditProfilePage(HttpSession session, Model model) {
        if (session.getAttribute("user_id") == null) {
            return "redirect:/login";
        }


        if (!model.containsAttribute("profileEdit")) {
            UserEditProfileBindingModel profileEdit = userService.getEditModel((UUID) session.getAttribute("user_id"));
            model.addAttribute("profileEdit", profileEdit);
        }

        return "edit-profile";
    }

    @PatchMapping("edit-profile")
    public ModelAndView editUserProfile(
            ModelAndView modelAndView,
            @ModelAttribute("profileEdit") @Valid UserEditProfileBindingModel profileEdit,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileEdit", bindingResult);
            redirectAttributes.addFlashAttribute("profileEdit", profileEdit);
            modelAndView.setViewName("redirect:/edit-profile");
        } else {
            userService.doEdit((UUID) session.getAttribute("user_id"), profileEdit);
            modelAndView.setViewName("redirect:/home");
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

}
