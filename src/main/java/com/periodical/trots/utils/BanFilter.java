package com.periodical.trots.utils;

import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class BanFilter implements Filter {

    private final UserServiceImpl userService;

    public BanFilter(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserEntity user = (UserEntity) ((HttpServletRequest) servletRequest).getSession().getAttribute("USER");
        if (user != null && userService.findByUsername(user.getUsername()).getBanStatus() != null) {
            ((HttpServletRequest) servletRequest).getSession().invalidate();
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}