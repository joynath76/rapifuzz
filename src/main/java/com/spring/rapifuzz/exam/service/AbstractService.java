package com.spring.rapifuzz.exam.service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public abstract class AbstractService {

    @Autowired
    private HttpSession httpSession;

    protected String getLoggedInUsername() {
        return (String) httpSession.getAttribute("username");
    }

    protected Long getLoggedInUserId() {
        return (Long) httpSession.getAttribute("userId");
    }

}
