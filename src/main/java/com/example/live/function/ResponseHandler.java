package com.example.live.function;
import org.springframework.http.*;
import java.util.*;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error,String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", error);
            map.put("message", message);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess",false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map,status);
        }
    }

    public static ResponseEntity<Object> generateResponseWRedis(HttpStatus status, boolean error,String message, String tokenRedis, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", error);
            map.put("message", message);
            map.put("tokenRedis", tokenRedis);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess",false);
            map.put("message", e.getMessage());
            map.put("tokenRedis", tokenRedis);
            map.put("data", null);
            return new ResponseEntity<Object>(map,status);
        }
    }

    public static ResponseEntity<Object> generateResponseXX(HttpStatus status, boolean error,String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", error);
            map.put("message", message);

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess",false);
            map.put("message", e.getMessage());
            return new ResponseEntity<Object>(map,status);
        }
    }

}