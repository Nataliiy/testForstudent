package com.periodical.trots.controllers.user;

import com.periodical.trots.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class BalanceController {

    private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);

    private final UserServiceImpl userService;

    public BalanceController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/top-up")
    public String topUpBalance(){
        return "BalancePage";
    }

    @PostMapping("/top-up")
    public String topUpBalancePost(RedirectAttributes redirectAttributes, @RequestParam("balance") BigDecimal balance, HttpServletRequest request){
        BigDecimal currentBalance = (BigDecimal) request.getSession().getAttribute("BALANCE");
        Integer userId = (Integer) request.getSession().getAttribute("ID");
        BigDecimal updatedBalance = userService.topUpBalance(balance, currentBalance, userId);
        String lang = String.valueOf(LocaleContextHolder.getLocale());
        if (lang.equals("en_US") || lang.equals("en")) {
            redirectAttributes.addFlashAttribute("ex", "Balance u");
        }else{
            redirectAttributes.addFlashAttribute("ex", "Баланс поповнено");
        }
        request.getSession().setAttribute("BALANCE", updatedBalance);
        logger.info("Balance top-uped -->" + userId + ", " + balance);
        return "redirect:/top-up";
    }
}
