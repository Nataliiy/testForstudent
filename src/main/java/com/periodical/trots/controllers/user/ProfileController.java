package com.periodical.trots.controllers.user;

import com.periodical.trots.entities.PeriodicalHasReceiptEntity;
import com.periodical.trots.services.PeriodicalHasReceiptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    private final PeriodicalHasReceiptService periodicalHasReceiptService;

    public ProfileController(PeriodicalHasReceiptService periodicalHasReceiptService) {
        this.periodicalHasReceiptService = periodicalHasReceiptService;
    }

    @GetMapping("/profile")
    public String ordersOfUsers(Model model, HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("ID");
        List<PeriodicalHasReceiptEntity> receiptEntities = periodicalHasReceiptService.findAllReceiptOfUser(userId);
        model.addAttribute("receipts", receiptEntities);
        return "ProfilePage";
    }
}
