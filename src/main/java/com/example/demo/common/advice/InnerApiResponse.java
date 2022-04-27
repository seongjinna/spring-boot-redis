package com.example.demo.common.advice;

import lombok.Data;

@Data
public class InnerApiResponse<T> {
    private int status;
    private String message;
    private T data;
}
