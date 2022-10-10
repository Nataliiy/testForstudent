package com.periodical.trots.controllers.user;

import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.services.SecurityService;
import com.periodical.trots.services.UserService;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    private final UserServiceImpl userServiceImpl;

    private final SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserServiceImpl userServiceImpl, SecurityService securityService) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
        this.securityService = securityService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/shop?page=0";
        }
        model.addAttribute("userForm", new UserEntity());

        return "UserRegistrationPage";
    }

    @PostMapping("/registration")
    public String registration(RedirectAttributes redirectAttributes, @Valid @ModelAttribute("userForm") UserEntity userForm, Errors errors) {
        String lang = String.valueOf(LocaleContextHolder.getLocale());
        if (errors.hasErrors()) {
            return "UserRegistrationPage";
        }
        if (userService.findByUsername(userForm.getUsername()) != null) {
            langEx(redirectAttributes, lang, "User with such username already exist", "Користувач з таким нікнеймом уже існує");
            return "redirect:/registration";
        } else if (userServiceImpl.findUserByEmail(userForm.getEmail()) != null) {
            langEx(redirectAttributes, lang, "User with such email already exist", "Користувач з таким мейлом уже існує");
            return "redirect:/registration";
        } else if (userServiceImpl.findUserByTelephone(userForm.getTelephone()) != null) {
            langEx(redirectAttributes, lang, "User with such telephone already exist", "Користувач з таким телефоном уже існує");
            return "redirect:/registration";
        }

        userService.save(userForm);
        logger.info("User registered --> " + userForm.getUsername());

        langEx(redirectAttributes, lang, "User successfully registered", "Користувач успішно зареєстрований");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (securityService.isAuthenticated()) {
            return "redirect:/shop?page=0";
        }
        String lang = String.valueOf(LocaleContextHolder.getLocale());
        if (error != null) {
            if (lang.equals("en_US") || lang.equals("en")) {
                model.addAttribute("error", "Your username and password is invalid.");
            } else {
                model.addAttribute("error", "Логін чи пароль не коректні");
            }
        }
        return "LoginPage";
    }

    private void langEx(RedirectAttributes redirectAttributes, String lang, String s, String s2) {
        if (lang.equals("en_US") || lang.equals("en")) {
            redirectAttributes.addFlashAttribute("ex", s);
        } else {
            redirectAttributes.addFlashAttribute("ex", s2);
        }
    }

}
