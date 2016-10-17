package com.dj.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 10/15/2016.
 */
public class CustomResponse {
    private boolean success = false;
    private List<String> errors = new ArrayList<String>();

    public CustomResponse(boolean success) {
        this.success = success;
    }

    public void addError(String message) {
        errors.add(message);
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }

}
