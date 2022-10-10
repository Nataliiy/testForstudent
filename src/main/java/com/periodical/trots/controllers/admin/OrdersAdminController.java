package com.periodical.trots.controllers.admin;

import com.periodical.trots.controllers.user.BalanceController;
import com.periodical.trots.entities.PeriodicalHasReceiptEntity;
import com.periodical.trots.entities.ReceiptEntity;
import com.periodical.trots.services.ReceiptService;
import com.periodical.trots.services.StatusService;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrdersAdminController {
    private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);
    private final ReceiptService receiptService;
    private final StatusService statusService;
    private final UserServiceImpl userService;
    public OrdersAdminController(ReceiptService receiptService, StatusService statusService, UserServiceImpl userService) {
        this.receiptService = receiptService;
        this.statusService = statusService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String periodicalsPageForAdmin(Model model) {
        List<ReceiptEntity> list = receiptService.getAllReceiptForAdmin();

        BigDecimal priceStart;

        List<BigDecimal> prices = new ArrayList<>();

        for (int i = 0; i< list.size(); i++) {
            BigDecimal priceFinal = BigDecimal.ZERO;
            List<PeriodicalHasReceiptEntity> listOfPerHasEnt = new ArrayList<>(list.get(i).getReceiptEntities());
            for (PeriodicalHasReceiptEntity r : listOfPerHasEnt){
                priceStart = r.getPricePerMonth();
                priceFinal = priceFinal.add(priceStart);
            }
            prices.add(priceFinal);
        }
        model.addAttribute("prices", prices);
        model.addAttribute("receipt", list);
        return "OrdersPageForAdmin";
    }

    @PostMapping("/accept-order")
    public String acceptOrderByAdmin(RedirectAttributes redirectAttributes, @RequestParam("receiptId") Integer receiptId) {
        receiptService.acceptOrderByAdmin(receiptId, statusService.getStatusById(3));
        langEx(redirectAttributes, "Order was discarded", "Замовлення було відхилено");
        logger.info("Order accepted --> " + receiptId);
        return "redirect:/orders";
    }

    @PostMapping("/discard-order")
    public String discardOrderByAdmin(RedirectAttributes redirectAttributes, @RequestParam("receiptId") Integer receiptId) {
        ReceiptEntity receipt = receiptService.getReceiptById(receiptId);
        BigDecimal balanceOfUser = receipt.getUser().getBalance();
        List<PeriodicalHasReceiptEntity> listOfPerHasEnt = new ArrayList<>(receipt.getReceiptEntities());
        BigDecimal priceStart;
        BigDecimal priceFinal = BigDecimal.ZERO;
        for (PeriodicalHasReceiptEntity r : listOfPerHasEnt){
            priceStart = r.getPricePerMonth();
            priceFinal = priceFinal.add(priceStart);
        }
        balanceOfUser = balanceOfUser.add(priceFinal);
        userService.updateBalanceAfterPayment(receipt.getUser().getUsername(), balanceOfUser);

        receiptService.discardOrderByAdmin(receiptId, statusService.getStatusById(2));

        langEx(redirectAttributes, "Order was discarded", "Замовлення було відхилено");
        logger.info("Order discarded --> " + receiptId);
        return "redirect:/orders";
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
