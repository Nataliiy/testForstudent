package com.periodical.trots.controllers.user;

import com.periodical.trots.entities.Cart;
import com.periodical.trots.entities.PeriodicalHasSubjectEntity;
import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.services.PeriodicalHasSubjectService;
import com.periodical.trots.services.SubjectService;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private final UserServiceImpl userService;

    private final SubjectService subjectService;

    private final PeriodicalHasSubjectService periodicalHasSubjectService;

    public ShopController(UserServiceImpl userService, SubjectService subjectService, PeriodicalHasSubjectService periodicalHasSubjectService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.periodicalHasSubjectService = periodicalHasSubjectService;
    }

    @GetMapping
    @RequestMapping("/shop")
    public String shopPage(Model model, @RequestParam("page") Integer page, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("ID");
        String category = (String) request.getSession().getAttribute("category");
        String order = (String) request.getSession().getAttribute("order");
        String sort = (String) request.getSession().getAttribute("sort");
        String searchField = (String) request.getSession().getAttribute("searchField");
        model.addAttribute("page", page);

        Page<PeriodicalHasSubjectEntity> pageWeb;

        if (category == null || category.equals("all")) {
            if (searchField == null || searchField.equals("")) {
                if (sort != null && !sort.equals("ws")) {
                    pageWeb = periodicalHasSubjectService.findAllWithSort(page, sort, order);
                } else {
                    pageWeb = periodicalHasSubjectService.findAll(page);
                }
            } else {
                if (sort != null && !sort.equals("ws")) {
                    pageWeb = periodicalHasSubjectService.findAllByTitle(page, sort, searchField, order);
                } else {
                    pageWeb = periodicalHasSubjectService.findAllByTitle(page, searchField);
                }
            }
        } else {
            if (searchField == null || searchField.equals("")) {
                if (sort != null && !sort.equals("ws")) {
                    pageWeb = periodicalHasSubjectService.findAllWithCategory(page, sort, category, order);
                } else {
                    pageWeb = periodicalHasSubjectService.findAllWithCategory(page, category);
                }
            } else {
                if (sort != null && !sort.equals("ws")) {
                    pageWeb = periodicalHasSubjectService.findAllWithCategoryAndTitle(page, sort, category, searchField, order);
                } else {
                    pageWeb = periodicalHasSubjectService.findAllWithCategoryAndTitle(page, category, searchField);
                }
            }
        }

        model.addAttribute("periodicals", pageWeb);

        model.addAttribute("countOfPages", pageWeb.getTotalPages());


        if (userId != null) {
            UserEntity user = userService.findUserById(userId);
            BigDecimal balance = user.getBalance();
            request.getSession().setAttribute("BALANCE", balance);
        }

        model.addAttribute("subjects", subjectService.findAll());


        List<Cart> cartList = (List<Cart>) request.getSession().getAttribute("cart-list");

        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        request.getSession().setAttribute("cart_list", cartList);

        return "ShopPage";
    }

    @PostMapping("/shop")
    public String postShop(HttpServletRequest request, @RequestParam(value = "categoryPer") String category, @RequestParam(value = "sort") String sort, @RequestParam(value = "order") String order, @RequestParam(value = "searchField") String searchField, @RequestParam("page") Integer page) {
        request.getSession().setAttribute("category", category);
        request.getSession().setAttribute("sort", sort);
        request.getSession().setAttribute("order", order);
        request.getSession().setAttribute("searchField", searchField);
        logger.info("Sorting success");
        return "redirect:/shop?page=" + page;
    }

}
