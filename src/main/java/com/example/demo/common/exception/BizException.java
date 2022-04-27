package com.example.demo.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends Exception{

    private String code;
    private Message msg;

    public BizException() {
        super();
    }

    public BizException(String code, String title, String content) {
        super();

        this.code = code;
        this.msg = Message.builder()
                .title(title)
                .content(content)
                .build();
    }

    public BizException(String code, String message) {
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

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
