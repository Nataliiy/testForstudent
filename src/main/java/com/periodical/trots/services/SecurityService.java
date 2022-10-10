package com.periodical.trots.services;

public interface SecurityService {
    boolean isAuthenticated();

    void autoLogin(String username, String password);
}
