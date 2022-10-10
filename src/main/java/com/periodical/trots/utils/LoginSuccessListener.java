package com.periodical.trots.utils;

import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessListener.class);

    private final UserServiceImpl userService;

    private final HttpServletRequest request;

    public LoginSuccessListener(UserServiceImpl userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent evt) {
        String currentUserName = evt.getAuthentication().getName();
        UserEntity user = userService.findByUsername(currentUserName);
        DecimalFormat dcf = new DecimalFormat("#.##");
        request.getSession().setAttribute("decimalFormat", dcf);
        request.getSession().setAttribute("ROLE", user.getRole());
        request.getSession().setAttribute("ID", user.getId());
        request.getSession().setAttribute("USER", user);
        logger.info("User -> " + evt.getAuthentication().getName() + ", role -> " + user.getRole());
    }
}
