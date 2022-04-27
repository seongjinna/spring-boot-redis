package com.example.demo.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends Exception{

    private String code;
    private Message msg;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String code, String title, String content) {
        super();

        this.code = code;
        this.msg = Message.builder()
                .title(title)
                .content(content)
                .build();
    }

    public BadRequestException(String code, String message) {
        super();

        this.code = code;

        String title = "";
        String content = "";

        if (message.contains("||")) {
            String[] msgs = message.split("\\|\\|");
            title = msgs[0];
            content = msgs[1];
        }
        else {
            content = message;
        }

        this.msg = Message.builder()
                .title(title)
                .content(content)
                .build();
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
