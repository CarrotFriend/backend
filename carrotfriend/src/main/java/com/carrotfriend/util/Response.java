package com.carrotfriend.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;

@Component
public class Response {
    public ResponseEntity<?> success(Object data, String msg, HttpStatus status){
        Body body = Body.builder()
                .state(status.value())
                .data(data)
                .message(msg)
                .result("success")
                .error(Collections.emptyList())
                .build();
        return ResponseEntity.ok(body);
    }
    public ResponseEntity<?> success(Object data){
        return success(data,null, HttpStatus.OK);
    }
    public ResponseEntity<?> success(String msg){
        return success(Collections.emptyList(), msg, HttpStatus.OK);
    }

    public ResponseEntity<?> fail(Object data, String msg, HttpStatus status){
        Body body = Body.builder()
                .state(status.value())
                .data(data)
                .message(msg)
                .result("fail")
                .error(Collections.emptyList())
                .build();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> fail(String msg, HttpStatus status){
        return fail(Collections.emptyList(), msg, status);
    }

    public ResponseEntity<?> invalidField(LinkedHashMap<String, String> error){
        Body body = Body.builder()
                .state(HttpStatus.BAD_REQUEST.value())
                .data(Collections.emptyList())
                .result("fail")
                .message("")
                .error(error)
                .build();
        return ResponseEntity.ok(body);
    }
    @Getter @Builder @ToString
    static class Body{
        private int state;
        private String result;
        private String message;
        private Object data;
        private Object error;
    }
}
