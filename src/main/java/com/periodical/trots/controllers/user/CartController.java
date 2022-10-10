package com.periodical.trots.controllers.user;

import com.periodical.trots.entities.*;
import com.periodical.trots.services.PeriodicalHasReceiptService;
import com.periodical.trots.services.PeriodicalService;
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

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final StatusService statusService;

    private final ReceiptService receiptService;

    private final PeriodicalHasReceiptService periodicalHasReceiptService;

    private final PeriodicalService periodicalService;

    private final UserServiceImpl userService;

    public CartController(StatusService statusService, ReceiptService receiptService, PeriodicalHasReceiptService periodicalHasReceiptService, PeriodicalService periodicalService, UserServiceImpl userService) {
        this.statusService = statusService;
        this.receiptService = receiptService;
        this.periodicalHasReceiptService = periodicalHasReceiptService;
        this.periodicalService = periodicalService;
        this.userService = userService;
    }

    @PostMapping("/order-all")
    public String orderAllFromCart(RedirectAttributes redirectAttributes, HttpServletRequest req, @RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("email") String email, @RequestParam("telephone") String telephone, @RequestParam("address") String address) {
        Integer id = (Integer) req.getSession().getAttribute("ID");
        BigDecimal actualBalance = (BigDecimal) req.getSession().getAttribute("BALANCE");
        BigDecimal totalPrice = (BigDecimal) req.getSession().getAttribute("totalPrice");
        List<Cart> cart_list = (List<Cart>) req.getSession().getAttribute("cart-list");
        String lang = String.valueOf(LocaleContextHolder.getLocale());

        if (cart_list != null && id != null && actualBalance.compareTo(totalPrice) > 0) {
            UserEntity user = (UserEntity) req.getSession().getAttribute("USER");
            ReceiptEntity receiptEntity = new ReceiptEntity();
            StatusEntity status = statusService.getStatusById(1);
            receiptEntity.setStatus(status);
            receiptEntity.setAdress(address);
            receiptEntity.setTelephoneNumber(telephone);
            receiptEntity.setEmail(email);
            receiptEntity.setName(name);
            receiptEntity.setSurname(surname);
            receiptEntity.setUser(user);
            Integer receiptId = receiptService.saveReceipt(receiptEntity);
            ReceiptEntity receipt = receiptService.getReceiptById(receiptId);
            PeriodicalHasReceiptEntity periodicalHasReceipt = new PeriodicalHasReceiptEntity();
            for (Cart c : cart_list) {
                PeriodicalHasReceiptEntityId periodicalHasReceiptEntityId = new PeriodicalHasReceiptEntityId();
                periodicalHasReceipt.setmReceipt(receipt);
                periodicalHasReceipt.setPeriodical(c.getPeriodical());
                periodicalHasReceipt.setId(periodicalHasReceiptEntityId);
                BigDecimal pricePerMonth = c.getPeriodical().getPricePerMonth();
                BigDecimal fullPrice = pricePerMonth.multiply(BigDecimal.valueOf(c.getMonths()));
                periodicalHasReceipt.setPricePerMonth(fullPrice);
                periodicalHasReceipt.setNumberOfMonth(c.getMonths());

                periodicalHasReceiptService.saveOrder(periodicalHasReceipt);
            }
            actualBalance = actualBalance.subtract(totalPrice);
            userService.updateBalanceAfterPayment(user.getUsername(), actualBalance);
            req.getSession().setAttribute("BALANCE", actualBalance);
            cart_list.clear();
            langEx(redirectAttributes, lang, "All periodicals were ordered", "Всі видання були замовлені");
        } else {
            langEx(redirectAttributes, lang, "Check your balance", "Перевірте баланс");
        }
        logger.info("All periodicals were ordered");
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGetMethod(HttpServletRequest request, Model model) {
        List<Cart> cart_list = (List<Cart>) request.getSession().getAttribute("cart-list");
        UserEntity user = (UserEntity) request.getSession().getAttribute("USER");
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (cart_list != null) {
            model.addAttribute("cartPeriodical", cart_list);
            BigDecimal pricePer;
            for (Cart c : cart_list) {
                pricePer = c.getPeriodical().getPricePerMonth();
                totalPrice = totalPrice.add(pricePer).multiply(BigDecimal.valueOf(c.getMonths()));
            }
            request.getSession().setAttribute("totalPrice", totalPrice);
        }
        model.addAttribute("user", user);
        return "CartPage";
    }

    @GetMapping("/inc-dec")
    private String incDec(@RequestParam("action") String action, @RequestParam("id") Integer id, HttpServletRequest request) {
        ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");

        if (action != null && id >= 1) {
            if (action.equals("inc")) {
                for (Cart c : cart_list) {
                    if (c.getPeriodical().getSellId() == id && c.getMonths() < 12) {
                        int month = c.getMonths();
                        month++;
                        c.setMonths(month);
                        BigDecimal price = c.getPeriodical().getPricePerMonth();
                        c.setTotalPrice(price.multiply(BigDecimal.valueOf(c.getMonths())));
                        break;
                    }
                }
                return "redirect:/cart";
            }
            if (action.equals("dec")) {
                for (Cart c : cart_list) {
                    if (c.getPeriodical().getSellId() == id && c.getMonths() > 1) {
                        int month = c.getMonths();
                        month--;
                        c.setMonths(month);
                        BigDecimal price = c.getPeriodical().getPricePerMonth();
                        c.setTotalPrice(price.multiply(BigDecimal.valueOf(c.getMonths())));
                        break;
                    }
                }
                return "redirect:/cart";
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/remove-record")
    public String removePeriodical(@RequestParam("id") Integer id, HttpServletRequest request) {
        if (id != null) {
            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
            if (cart_list != null) {
                for (Cart c : cart_list) {
                    if (c.getPeriodical().getSellId() == id) {
                        cart_list.remove(cart_list.indexOf(c));
                        break;
                    }
                }
                return "redirect:/cart";
            }
        }
        return "redirect:/cart";

    }

    @PostMapping("/add-cart")
    public String addPeriodicalToCart(RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam("periodicalId") Integer periodicalId, @RequestParam("page") Integer page) {
        List<Cart> cartList = new ArrayList<>();

        String lang = String.valueOf(LocaleContextHolder.getLocale());
        langEx(redirectAttributes, lang, "Periodical was added to cart", "Видання додано до корзини");

        Cart cartObject = new Cart();
        PeriodicalEntity periodical = periodicalService.getPeriodicalById(periodicalId);
        cartObject.setPeriodical(periodical);
        BigDecimal pricePerMonth = periodical.getPricePerMonth();
        cartObject.setTotalPrice(pricePerMonth);
        cartObject.setMonths(1);

        List<Cart> cart_list = (List<Cart>) request.getSession().getAttribute("cart-list");

        if (cart_list == null) {
            cartList.add(cartObject);
            request.getSession().setAttribute("cart-list", cartList);
            return "redirect:/shop?page=" + page;
        } else {
            cartList = cart_list;
            boolean exist = false;
            for (Cart c : cartList) {
                if (c.getPeriodical().getSellId() == periodicalId) {
                    exist = true;
                    langEx(redirectAttributes, lang, "Periodical already in cart", "Видання уже в корзині");
                    return "redirect:/shop?page=" + page;
                }
            }
            if (!exist) {
                cartList.add(cartObject);
                return "redirect:/shop?page=" + page;
            }
        }
        logger.info("Periodical was added to cart --> " + periodicalId);
        return "redirect:/shop?page=" + page;
    }

    @PostMapping("/buy-now")
    public String buyNow(RedirectAttributes redirectAttributes, @RequestParam("periodicalId") Integer periodicalId, @RequestParam("page") Integer page, HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("USER");
        BigDecimal actualBalance = (BigDecimal) request.getSession().getAttribute("BALANCE");
        String lang = String.valueOf(LocaleContextHolder.getLocale());
        PeriodicalEntity periodical = periodicalService.getPeriodicalById(periodicalId);
        BigDecimal pricePerMonth = periodical.getPricePerMonth();
        if (actualBalance.compareTo(pricePerMonth) < 0) {
            langEx(redirectAttributes, lang, "Periodical wasn't ordered, check balance", "Видання не було замовлено, перевірте баланс");
            return "redirect:/shop?page=" + page;
        } else {
            ReceiptEntity receiptEntity = new ReceiptEntity();
            StatusEntity status = statusService.getStatusById(1);
            receiptEntity.setStatus(status);
            receiptEntity.setAdress(user.getAddress());
            receiptEntity.setTelephoneNumber(user.getTelephone());
            receiptEntity.setEmail(user.getEmail());
            receiptEntity.setName(user.getName());
            receiptEntity.setSurname(user.getSurname());
            receiptEntity.setUser(user);
            Integer receiptId = receiptService.saveReceipt(receiptEntity);

            PeriodicalHasReceiptEntity periodicalHasReceipt = new PeriodicalHasReceiptEntity();
            ReceiptEntity receipt = receiptService.getReceiptById(receiptId);
            PeriodicalHasReceiptEntityId periodicalHasReceiptEntityId = new PeriodicalHasReceiptEntityId();
            periodicalHasReceiptEntityId.setReceiptId(receiptId);
            periodicalHasReceiptEntityId.setPeriodicalSellId(periodicalId);
            periodicalHasReceipt.setmReceipt(receipt);
            periodicalHasReceipt.setPeriodical(periodical);
            periodicalHasReceipt.setId(periodicalHasReceiptEntityId);
            periodicalHasReceipt.setPricePerMonth(periodical.getPricePerMonth());
            periodicalHasReceipt.setNumberOfMonth(1);

            actualBalance = actualBalance.subtract(pricePerMonth);
            userService.updateBalanceAfterPayment(user.getUsername(), actualBalance);
            request.getSession().setAttribute("BALANCE", actualBalance);

            periodicalHasReceiptService.saveOrder(periodicalHasReceipt);
            logger.info("Periodical was ordered --> " + periodicalId);
            langEx(redirectAttributes, lang, "Periodical was ordered", "Видання було замовлено");
            return "redirect:/shop?page=" + page;
        }
    }

    private void langEx(RedirectAttributes redirectAttributes, String lang, String s, String s2) {
        if (lang.equals("en_US") || lang.equals("en")) {
            redirectAttributes.addFlashAttribute("ex", s);
        } else {
            redirectAttributes.addFlashAttribute("ex", s2);
        }
    }
}
