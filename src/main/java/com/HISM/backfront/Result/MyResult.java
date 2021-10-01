package com.HISM.backfront.Result;

import java.util.HashMap;
import java.util.Map;

public class MyResult {
    private Map<String, Object> result;
    private Map<String, Object> message;

    public MyResult() {
        result = new HashMap<>(3);
        message = new HashMap<>();
        result.put("Status", false);
        result.put("Message", message);
    }

    public void add(String key, Object value) {
        message.put(key, value);
    }

    public HashMap<String, Object> getResult() {
        return (HashMap<String, Object>) result;
    }

}
