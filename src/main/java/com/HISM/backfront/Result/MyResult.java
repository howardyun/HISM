package com.HISM.backfront.Result;

import java.util.HashMap;
import java.util.Map;

public class MyResult {
    private Map<String, Object> result;


    public MyResult() {
        result = new HashMap<>(3);

        result.put("status", false);

    }

    public void add(String key, Object value) {
        result.put(key, value);
    }

    public void changeStatus(boolean status){
        result.put("status",status);
    }

    public HashMap<String, Object> getResult() {
        return (HashMap<String, Object>) result;
    }

}
