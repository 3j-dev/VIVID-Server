package com.chicplay.mediaserver.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@Slf4j
@Service
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated (HttpSessionEvent se) {
        log.info("qteqt");
        se.getSession().setMaxInactiveInterval(30); //30초
    }

    @Override
    public void sessionDestroyed (HttpSessionEvent se) {

    }
}