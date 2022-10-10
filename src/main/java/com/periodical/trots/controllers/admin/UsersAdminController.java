package com.periodical.trots.controllers.admin;

import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UsersAdminController {

    private final UserServiceImpl userService;

    public UsersAdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String allUsersPageAdmin(Model model){
        model.addAttribute("allUsers", userService.getAll());
        model.addAttribute("userForm", new UserEntity());
        return "UsersPageForAdmin";
    }

    @PostMapping("/add-user")
    public String addUserByAdmin(RedirectAttributes redirectAttributes, @Valid @ModelAttribute("userForm") UserEntity userForm, Errors errors, BindingResult bindingResult) {
        if (errors.hasErrors()){
            return "UsersPageForAdmin";
        }
        if (userService.findByUsername(userForm.getUsername())!=null){
            langEx(redirectAttributes, "User with such username already exist", "Користувач з таким нікнеймом уже існує");
            return "redirect:/users";
        }else if(userService.findUserByEmail(userForm.getEmail())!=null){
            langEx(redirectAttributes, "User with such email already exist", "Користувач з таким мейлом уже існує");
            return "redirect:/users";
        } else if(userService.findUserByTelephone(userForm.getTelephone())!=null){
            langEx(redirectAttributes, "User with such telephone already exist", "Користувач з таким телефоном уже існує");
            return "redirect:/users";
        }

        userService.saveUserByAdmin(userForm);

        langEx(redirectAttributes,"User successfully registered", "Користувач успішно зареєстрований");

        return "redirect:/users";
    }

    @PostMapping("/ban-user")
    public String banUserByAdmin(RedirectAttributes redirectAttributes, @RequestParam("userId") Integer userId){
        userService.banUserById(userId);
        langEx(redirectAttributes, "Ban status was changed", "Статус блокування було змінено");
        return "redirect:/users";
    }

    @PostMapping("/delete-user")
    public String deleteUserByAdmin(RedirectAttributes redirectAttributes, @RequestParam("userId") Integer userId){
        userService.deleteUserById(userId);
        langEx(redirectAttributes, "User was deleted", "Користувача видалено");
        return "redirect:/users";
    }

    private void langEx(RedirectAttributes redirectAttributes, String s, String s2) {
        String lang = String.valueOf(LocaleContextHolder.getLocale());
        if (lang.equals("en_US") || lang.equals("en")) {
            redirectAttributes.addFlashAttribute("ex", s);
        } else {
            redirectAttributes.addFlashAttribute("ex", s2);
        }
    }
}
