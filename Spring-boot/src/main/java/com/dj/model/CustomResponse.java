package com.dj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Darshan on 10/15/2016.
 */
public class CustomResponse {
    private boolean success = false;
    private List<String> errors = new ArrayList<String>();
    private Map<String, String> data = new HashMap<>();

    public CustomResponse(boolean success) {
        this.success = success;
    }

    public void addError(String message) {
        errors.add(message);
    }

    public void addData(String key, String value) {
        data.put(key, value);
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Map<String, String> getData() {
        return data;
    }
}
