package com.example.demo.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemException extends Exception {

    private String code;
    private Message msg;

    public SystemException() {
        super();
    }

    public SystemException(String code, String title, String content) {
        super();

        this.code = code;
        this.msg = Message.builder()
                .title(title)
                .content(content)
                .build();
    }

    public SystemException(String code, String message) {
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

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
