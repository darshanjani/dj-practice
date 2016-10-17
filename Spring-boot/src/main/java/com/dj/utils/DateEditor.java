package com.dj.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Darshan on 10/15/2016.
 */
public class DateEditor extends PropertyEditorSupport {

    public void setAsText(String value) {
        try {
            setValue(new SimpleDateFormat("dd/MM/yyyy").parse(value));
        } catch (ParseException e) {
            setValue(null);
        }
    }

    public String getAsText() {
        String s = "";
        if (getValue() != null) {
            s = new SimpleDateFormat("dd/MM/yyyy").format((Date) getValue());
        }
        return s;
    }
}