package com.dj.utils;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * Created by Darshan on 10/15/2016.
 */
@ControllerAdvice
public class GlobalBindingInitializer {

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}
