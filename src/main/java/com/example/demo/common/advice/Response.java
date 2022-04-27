package com.example.demo.common.advice;

import com.example.demo.common.exception.Message;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    public Response(Errors enumErrors) {
        this.error = enumErrors.getCode();
        this.message = enumErrors.getMessage();
    }

    private String error;
    private Message message;
    private T data;
}
